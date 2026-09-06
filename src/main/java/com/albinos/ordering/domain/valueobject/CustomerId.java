package com.albinos.ordering.domain.valueobject;

import com.albinos.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {

    public CustomerId(){
        this(IdGenerator.generateTimeBasedUUID());
    }

    public CustomerId(UUID value){
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
