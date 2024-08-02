package org.sonnetto.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentException extends RuntimeException {
    public PaymentException(Throwable cause) {
        super(cause);
    }
}
