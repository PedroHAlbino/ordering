package com.albinos.ordering.domain.valueobject;

import java.util.Objects;

import static com.albinos.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PRODUCT_NAME_IS_BLANK;

public record ProductName(String value) {

    public ProductName {
        Objects.requireNonNull(value);
        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_PRODUCT_NAME_IS_BLANK);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
