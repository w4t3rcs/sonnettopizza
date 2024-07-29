package org.sonnetto.price.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sonnetto.price.config.ApplicationContextContainer;
import org.springframework.web.reactive.function.client.WebClient;

public class DishIdValidator implements ConstraintValidator<DishId, Long> {
    private static final String DISH_URI = "http://dish-service:8083/api/v1.0/dishes/{id}";
    private WebClient webClient;

    @Override
    public void initialize(DishId constraintAnnotation) {
        this.webClient = ApplicationContextContainer.getApplicationContext()
                .getBean(WebClient.class);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return Boolean.TRUE.equals(webClient.head()
                .uri(DISH_URI, id)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false)
                .block());
    }
}
