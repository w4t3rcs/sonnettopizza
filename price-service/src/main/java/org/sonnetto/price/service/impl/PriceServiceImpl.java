package org.sonnetto.price.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sonnetto.price.dto.ConversionRequest;
import org.sonnetto.price.dto.ConversionResponse;
import org.sonnetto.price.dto.PriceRequest;
import org.sonnetto.price.dto.PriceResponse;
import org.sonnetto.price.entity.Price;
import org.sonnetto.price.exception.DishServiceUnavailableException;
import org.sonnetto.price.exception.PriceNotFoundException;
import org.sonnetto.price.repository.PriceRepository;
import org.sonnetto.price.service.CurrencyConverterService;
import org.sonnetto.price.service.PriceService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final CurrencyConverterService currencyConverterService;

    @Override
    @Caching(cacheable = @Cacheable("priceCache"))
    @Transactional
    @CircuitBreaker(name = "dish-service", fallbackMethod = "fallback")
    @TimeLimiter(name = "dish-service")
    @Retry(name = "dish-service")
    public CompletableFuture<PriceResponse> createPrice(PriceRequest priceRequest) {
        return CompletableFuture.supplyAsync(() -> PriceResponse.fromPrice(priceRepository.save(priceRequest.toPrice())));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<PriceResponse> getPrices(Pageable pageable) {
        return new PagedModel<>(priceRepository.findAll(pageable)
                .map(PriceResponse::fromPrice));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<PriceResponse> getConvertedPrices(Pageable pageable, String code) {
        return new PagedModel<>(priceRepository.findAll(pageable)
                .map(PriceResponse::fromPrice)
                .map(priceResponse -> this.produceConvertedPrice(priceResponse, code)));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<PriceResponse> getPricesByDishId(Long dishId, Pageable pageable) {
        return new PagedModel<>(priceRepository.findAllByDishId(dishId, pageable)
                .map(PriceResponse::fromPrice));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<PriceResponse> getConvertedPricesByDishId(Long dishId, Pageable pageable, String code) {
        return new PagedModel<>(priceRepository.findAllByDishId(dishId, pageable)
                .map(PriceResponse::fromPrice)
                .map(priceResponse -> this.produceConvertedPrice(priceResponse, code)));
    }

    @Override
    @Cacheable("priceCache")
    @Transactional(readOnly = true)
    public PriceResponse getPrice(Long id) {
        return PriceResponse.fromPrice(priceRepository.findById(id)
                .orElseThrow(PriceNotFoundException::new));
    }

    @Override
    @Cacheable("convertedPriceCache")
    @Transactional(readOnly = true)
    public PriceResponse getConvertedPrice(Long id, String code) {
        return this.produceConvertedPrice(PriceResponse.fromPrice(priceRepository.findById(id)
                .orElseThrow(PriceNotFoundException::new)), code);
    }

    @Override
    @Caching(put = @CachePut("priceCache"))
    @Transactional
    @CircuitBreaker(name = "dish-service", fallbackMethod = "fallback")
    @TimeLimiter(name = "dish-service")
    @Retry(name = "dish-service")
    public CompletableFuture<PriceResponse> updatePrice(Long id, PriceRequest priceRequest) {
        Price price = priceRepository.findById(id)
                .orElseThrow(PriceNotFoundException::new);
        if (priceRequest.getCode() != null) price.setCode(priceRequest.getCode());
        if (priceRequest.getValue() != null) price.setValue(priceRequest.getValue());
        if (priceRequest.getDishId() != null) price.setDishId(priceRequest.getDishId());
        return CompletableFuture.supplyAsync(() -> PriceResponse.fromPrice(priceRepository.save(price)));
    }

    @Override
    @Caching(evict = @CacheEvict("priceCache"))
    @Transactional
    public Long deletePrice(Long id) {
        priceRepository.deleteById(id);
        return id;
    }

    private PriceResponse produceConvertedPrice(PriceResponse priceResponse, String code) {
        ConversionResponse conversionResponse = currencyConverterService.convert(new ConversionRequest(priceResponse.getValue(), priceResponse.getCode(), code));
        priceResponse.setValue(conversionResponse.getConversionRate());
        priceResponse.setCode(conversionResponse.getCode());
        return priceResponse;
    }

    private CompletableFuture<PriceResponse> fallback(Throwable throwable) {
        log.error("{}\n{}" , throwable.getMessage(), Arrays.toString(throwable.getStackTrace()));
        throw new DishServiceUnavailableException(throwable);
    }
}
