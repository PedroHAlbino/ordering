package com.albinos.ordering.domain.model.valueobject;

import com.albinos.ordering.domain.model.validator.FielValidations;
import lombok.Builder;

import java.util.Objects;

public record Address(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode
) {
    @Builder(toBuilder = true)
    public Address  {
        FielValidations.requiresNonBlank(street);
        FielValidations.requiresNonBlank(neighborhood);
        FielValidations.requiresNonBlank(city);
        FielValidations.requiresNonBlank(state);
        Objects.requireNonNull(zipCode);
    }
}
