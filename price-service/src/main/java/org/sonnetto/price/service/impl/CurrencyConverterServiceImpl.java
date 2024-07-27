package org.sonnetto.price.service.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.price.dto.ConversionRequest;
import org.sonnetto.price.dto.ConversionResponse;
import org.sonnetto.price.service.CurrencyConverterService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    private final WebClient webClient;

    @Override
    public ConversionResponse convert(ConversionRequest conversionRequest) {
        return webClient.get()
                .uri("https://v6.exchangerate-api.com/v6/{key}/pair/{code}/{conversionCode}",
                        "10fb5c73e4bc0bcb94a0c15f", conversionRequest.getCode(), conversionRequest.getConversionCode())
                .retrieve()
                .bodyToMono(ConversionResponse.class)
                .map(conversionResponse -> {
                    conversionResponse.setConversionRate(conversionResponse.getConversionRate() * conversionRequest.getValue());
                    return conversionResponse;
                })
                .block();
    }
}
