package com.albinos.ordering.domain.exception;

import com.albinos.ordering.domain.valueobject.id.OrderId;

import java.io.EOFException;

public class OrderCannotBePlacedException extends DomainException{
    public OrderCannotBePlacedException(OrderId id) {
        super(String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NOT_ITEMS));
    }
}
