package com.albinos.ordering.domain.model.valueobject;

import lombok.Builder;

import java.util.Objects;

public record Billing(FullName fullName, Document document, Phone phone,Email email, Address address) {

    @Builder
    public Billing {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(email);
        Objects.requireNonNull(address);
    }
}
