package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.OrderStatusCannotBeChangedException;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderMarkAsReadyTest {

    @Test
    public void givenPaidOrder_whenMarkAsReady_shouldChangeToReady() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        order.markAsReady();

        Assertions.assertThat(order.isReady()).isTrue();
        Assertions.assertThat(order.readyAt()).isNotNull();
    }

    @Test
    public void givenDraftOrder_whenMarkAsReady_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        ThrowableAssert.ThrowingCallable task = order::markAsReady;

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class).isThrownBy(task);
        Assertions.assertThat(order.isReady()).isFalse();
        Assertions.assertThat(order.readyAt()).isNull();
    }

    @Test
    public void givenPlacedOrder_whenMarkAsReady_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        ThrowableAssert.ThrowingCallable task = order::markAsReady;

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class).isThrownBy(task);
        Assertions.assertThat(order.isReady()).isFalse();
        Assertions.assertThat(order.readyAt()).isNull();
    }
}
