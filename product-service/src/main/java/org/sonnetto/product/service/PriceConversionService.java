package org.sonnetto.product.service;

import org.sonnetto.product.document.Price;

public interface PriceConversionService {
    void convertPrice(Price price, String targetCurrency);
}
