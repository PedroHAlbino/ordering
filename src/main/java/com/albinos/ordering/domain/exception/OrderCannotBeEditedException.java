package com.albinos.ordering.domain.exception;

import com.albinos.ordering.domain.entity.OrderStatus;
import com.albinos.ordering.domain.valueobject.id.OrderId;

import static com.albinos.ordering.domain.exception.ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED;

public class OrderCannotBeEditedException extends DomainException {

    public OrderCannotBeEditedException(OrderId id, OrderStatus status) {
        super(String.format(ERROR_ORDER_CANNOT_BE_EDITED, id, status));
    }
}
