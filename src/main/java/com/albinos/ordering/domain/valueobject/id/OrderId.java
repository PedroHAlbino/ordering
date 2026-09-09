package com.albinos.ordering.domain.valueobject.id;

import com.albinos.ordering.domain.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

public record OrderId(TSID valeu) {

    public OrderId{
        Objects.requireNonNull(valeu);
    }

    public OrderId(){
        this(IdGenerator.generateTSID());
    }

    public OrderId(Long value){
        this(TSID.from(value));
    }

    public OrderId(String value){
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return valeu.toString();
    }
}
