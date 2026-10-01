package com.albinos.ordering.infrastructure.persistence.assembler;

import com.albinos.ordering.domain.model.entity.Order;
import com.albinos.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(Order order){
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderPersistenceEntity, Order order){
        orderPersistenceEntity.setId(order.id().valeu().toLong());
        orderPersistenceEntity.setCustomerId(order.customerId().value());
        orderPersistenceEntity.setTotalAmount(order.totalAmount().value());
        orderPersistenceEntity.setTotalItems(order.totalItems().value());
        orderPersistenceEntity.setStatus(order.status().name());
        orderPersistenceEntity.setPaymentMethod(order.paymentMethod() != null ? order.paymentMethod().name() : null);
        orderPersistenceEntity.setPlacedAt(order.placedAt());
        orderPersistenceEntity.setPaidAt(order.paidAt());
        orderPersistenceEntity.setCanceledAt(order.canceledAt());
        orderPersistenceEntity.setReadyAt(order.readyAt());
        return orderPersistenceEntity;
    }
}
