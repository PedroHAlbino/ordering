package com.albinos.ordering.domain.valueobject;

import com.albinos.ordering.domain.validator.FielValidations;

import static com.albinos.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email(String value) {
        FielValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
