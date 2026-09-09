package com.albinos.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

public record ShippingInfo(FullName fullName, Document document, Phone phone, Address address) {

    @Builder
    public ShippingInfo {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(address);
    }
}
