package org.sonnetto.order.validation;

import jakarta.validation.ConstraintValidator;

import java.lang.annotation.Annotation;

public interface ResourceValidator<A extends Annotation, T> extends ConstraintValidator<A, T> {
    boolean isResourceValid(String uri, Object... uriVariables);
}
