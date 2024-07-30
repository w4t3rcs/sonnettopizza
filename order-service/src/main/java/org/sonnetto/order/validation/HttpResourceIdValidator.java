package org.sonnetto.order.validation;

import lombok.Getter;
import org.sonnetto.order.config.ApplicationContextContainer;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.annotation.Annotation;

@Getter
public abstract class HttpResourceIdValidator<A extends Annotation, T> implements ResourceValidator<A, T> {
    private WebClient webClient;

    @Override
    public void initialize(A constraintAnnotation) {
        this.webClient = ApplicationContextContainer.getApplicationContext()
                .getBean(WebClient.class);
    }

    @Override
    public boolean isResourceValid(String uri, Object... uriVariables) {
        return Boolean.TRUE.equals(webClient.head()
                .uri(uri, uriVariables)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorReturn(false)
                .block());
    }
}
