package org.sonnetto.price.service.impl;

import org.sonnetto.price.dto.ConversionRequest;
import org.sonnetto.price.dto.ConversionResponse;
import org.sonnetto.price.service.CurrencyConverterService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {
    @Override
    public ConversionResponse convert(ConversionRequest conversionRequest) {
        return WebClient.create("https://v6.exchangerate-api.com/v6/10fb5c73e4bc0bcb94a0c15f/pair/%s/%s"
                        .formatted(conversionRequest.getCode(), conversionRequest.getConversionCode()))
                .get()
                .retrieve()
                .bodyToMono(ConversionResponse.class)
                .map(conversionResponse -> {
                    conversionResponse.setConversionRate(conversionResponse.getConversionRate() * conversionRequest.getValue());
                    return conversionResponse;
                })
                .block();
    }
}
