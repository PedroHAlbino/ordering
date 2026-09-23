package com.albinos.ordering.domain.model.valueobject;

import java.util.Objects;

import static com.albinos.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_BLANK;
import static com.albinos.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_NULL;

public record Document(String value) {

    public Document(String value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_DOCUMENT_IS_NULL);
        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_DOCUMENT_IS_BLANK);
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
