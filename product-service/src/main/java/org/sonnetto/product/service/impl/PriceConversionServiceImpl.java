package org.sonnetto.product.service.impl;

import org.sonnetto.product.document.Price;
import org.sonnetto.product.service.PriceConversionService;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

@Service
public class PriceConversionServiceImpl implements PriceConversionService {
    @Override
    public void convertPrice(Price price, String targetCurrency) {
        MonetaryAmountFactory<?> defaultAmountFactory = Monetary.getDefaultAmountFactory();
        MonetaryAmount currentPrice = defaultAmountFactory.setCurrency(price.getCurrency())
                .setNumber(price.getValue())
                .create();
        CurrencyConversion conversion = MonetaryConversions.getConversion(targetCurrency);
        MonetaryAmount convertedPrice = currentPrice.with(conversion);
        price.setValue(convertedPrice.getNumber().floatValue());
        price.setCurrency(targetCurrency);
    }
}
