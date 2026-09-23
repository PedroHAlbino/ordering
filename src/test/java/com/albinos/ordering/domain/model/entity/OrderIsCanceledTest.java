package com.albinos.ordering.domain.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderIsCanceledTest {

    @Test
    public void givenCanceledOrder_whenIsCanceled_shouldReturnTrue() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build();

        Assertions.assertThat(order.isCanceled()).isTrue();
    }

    @Test
    public void givenDraftOrder_whenIsCanceled_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        Assertions.assertThat(order.isCanceled()).isFalse();
    }

    @Test
    public void givenPlacedOrder_whenIsCanceled_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertThat(order.isCanceled()).isFalse();
    }

    @Test
    public void givenPaidOrder_whenIsCanceled_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        Assertions.assertThat(order.isCanceled()).isFalse();
    }

    @Test
    public void givenReadyOrder_whenIsCanceled_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        Assertions.assertThat(order.isCanceled()).isFalse();
    }
}
