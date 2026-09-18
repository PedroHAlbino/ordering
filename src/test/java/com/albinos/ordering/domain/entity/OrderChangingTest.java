package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.OrderCannotBeEditedException;
import com.albinos.ordering.domain.valueobject.Billing;
import com.albinos.ordering.domain.valueobject.Product;
import com.albinos.ordering.domain.valueobject.Quantity;
import com.albinos.ordering.domain.valueobject.Shipping;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class OrderChangingTest {

    @Test
    public void givenDraftOrder_whenAddItem_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        order.addItem(product, new Quantity(1));

        Assertions.assertThat(order.items()).hasSize(1);
    }

    @Test
    public void givenDraftOrder_whenChangeShipping_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Shipping shipping = OrderTestDataBuilder.aShippingAlt();

        order.changeShipping(shipping);

        Assertions.assertThat(order.shippin()).isEqualTo(shipping);
    }

    @Test
    public void givenDraftOrder_whenChangeBilling_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Billing billing = OrderTestDataBuilder.aBilling();

        order.changeBilling(billing);

        Assertions.assertThat(order.billing()).isEqualTo(billing);
    }

    @Test
    public void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);

        Assertions.assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    public void givenDraftOrder_whenChangeItemQuantity_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderItem orderItem = order.items().iterator().next();

        order.changeItemQuantity(orderItem.id(), new Quantity(9));

        Assertions.assertThat(orderItem.quantity()).isEqualTo(new Quantity(9));
    }

    @Test
    public void givenPlacedOrder_whenAddItem_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();

        ThrowableAssert.ThrowingCallable task = () -> order.addItem(product, new Quantity(1));

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }

    @Test
    public void givenPlacedOrder_whenChangeShipping_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Shipping shipping = OrderTestDataBuilder.aShippingAlt();

        ThrowableAssert.ThrowingCallable task = () -> order.changeShipping(shipping);

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }

    @Test
    public void givenPlacedOrder_whenChangeBilling_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Billing billing = OrderTestDataBuilder.aBilling();

        ThrowableAssert.ThrowingCallable task = () -> order.changeBilling(billing);

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }

    @Test
    public void givenPlacedOrder_whenChangePaymentMethod_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        ThrowableAssert.ThrowingCallable task = () -> order.changePaymentMethod(PaymentMethod.CREDIT_CARD);

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }

    @Test
    public void givenPlacedOrder_whenChangeItemQuantity_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        OrderItem orderItem = order.items().iterator().next();

        ThrowableAssert.ThrowingCallable task = () -> order.changeItemQuantity(orderItem.id(), new Quantity(9));

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }

    @Test
    public void givenPaidOrder_whenChangeBilling_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Billing billing = OrderTestDataBuilder.aBilling();

        ThrowableAssert.ThrowingCallable task = () -> order.changeBilling(billing);

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(task);
    }
}
