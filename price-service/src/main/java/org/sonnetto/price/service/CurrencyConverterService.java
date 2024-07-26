package org.sonnetto.price.service;

import org.sonnetto.price.dto.ConversionRequest;
import org.sonnetto.price.dto.ConversionResponse;

public interface CurrencyConverterService {
    ConversionResponse convert(ConversionRequest conversionRequest);
}
