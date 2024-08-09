package org.sonnetto.product.service;

import org.sonnetto.product.entity.Price;

public interface PriceConversionService {
    void convertPrice(Price price, String targetCurrency);
}
