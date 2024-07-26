package org.sonnetto.price.service;

import org.sonnetto.price.dto.PriceRequest;
import org.sonnetto.price.dto.PriceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

public interface PriceService {
    PriceResponse createPrice(PriceRequest priceRequest);

    PagedModel<PriceResponse> getPrices(Pageable pageable);

    PagedModel<PriceResponse> getConvertedPrices(Pageable pageable, String code);

    PagedModel<PriceResponse> getPricesByDishId(Long dishId, Pageable pageable);

    PagedModel<PriceResponse> getConvertedPricesByDishId(Long dishId, Pageable pageable, String code);

    PriceResponse getPrice(Long id);

    PriceResponse getConvertedPrice(Long id, String code);

    PriceResponse updatePrice(Long id, PriceRequest priceRequest);

    Long deletePrice(Long id);
}
