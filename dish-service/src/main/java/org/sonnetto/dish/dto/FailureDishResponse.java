package org.sonnetto.dish.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FailureDishResponse extends DishResponse {
    private final String message;

    public FailureDishResponse(String message) {
        super(-1L, "unavailable", null, null);
        this.message = message;
    }
}
