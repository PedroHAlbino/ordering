package com.albinos.ordering.domain.model.entity;

import com.albinos.ordering.domain.model.exception.OrderCannotBeEditedException;
import com.albinos.ordering.domain.model.exception.OrderDoesNotContainOrderItemException;
import com.albinos.ordering.domain.model.valueobject.id.OrderItemId;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderRemoveItemTest {

    @Test
    public void givenDraftOrderWithTwoItems_whenRemoveItem_shouldRemoveAndRecalculateTotals() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderItem orderItemToRemove = order.items().iterator().next();
        OrderItem remainingItem = order.items().stream()
                .filter(i -> !i.id().equals(orderItemToRemove.id()))
                .findFirst()
                .orElseThrow();

        order.removeItem(orderItemToRemove.id());

        Assertions.assertThat(order.items()).hasSize(1);
        Assertions.assertThat(order.items()).doesNotContain(orderItemToRemove);
        Assertions.assertThat(order.totalItems()).isEqualTo(remainingItem.quantity());
        Assertions.assertThat(order.totalAmount())
                .isEqualTo(remainingItem.totalAmount().add(order.shippin().cost()));
    }

    @Test
    public void givenDraftOrder_whenRemoveNonExistentItem_shouldGenerateException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderItemId nonExistentItemId = new OrderItemId();

        ThrowableAssert.ThrowingCallable task = () -> order.removeItem(nonExistentItemId);

        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class).isThrownBy(task);
    }

    @Test
    public void givenPlacedOrder_whenRemoveItem_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        OrderItem orderItem = order.items().iterator().next();

        ThrowableAssert.ThrowingCallable task = () -> order.removeItem(orderItem.id());

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }
}
