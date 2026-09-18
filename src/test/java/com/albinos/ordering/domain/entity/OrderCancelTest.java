package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.OrderStatusCannotBeChangedException;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderCancelTest {

    @Test
    public void givenDraftOrder_whenCancel_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        order.cancel();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isNotNull();
    }

    @Test
    public void givenPlacedOrder_whenCancel_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        order.cancel();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isNotNull();
    }

    @Test
    public void givenPaidOrder_whenCancel_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        order.cancel();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isNotNull();
    }

    @Test
    public void givenReadyOrder_whenCancel_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        order.cancel();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isNotNull();
    }

    @Test
    public void givenCanceledOrder_whenCancel_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build();
        var canceledAtBeforeSecondCancel = order.canceledAt();

        ThrowableAssert.ThrowingCallable task = order::cancel;

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class).isThrownBy(task);
        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isEqualTo(canceledAtBeforeSecondCancel);
    }
}
