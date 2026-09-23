package com.albinos.ordering.domain.model.valueobject.id;

import com.albinos.ordering.domain.model.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

public record OrderItemId(TSID valeu) {

    public OrderItemId {
        Objects.requireNonNull(valeu);
    }

    public OrderItemId(){

        this(IdGenerator.generateTSID());
    }

    public OrderItemId(Long value){
        this(TSID.from(value));
    }

    public OrderItemId(String value){
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return valeu.toString();
    }
}
