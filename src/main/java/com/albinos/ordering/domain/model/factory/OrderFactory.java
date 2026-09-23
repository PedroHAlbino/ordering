package com.albinos.ordering.domain.model.factory;

import com.albinos.ordering.domain.model.entity.Order;
import com.albinos.ordering.domain.model.entity.PaymentMethod;
import com.albinos.ordering.domain.model.valueobject.Billing;
import com.albinos.ordering.domain.model.valueobject.Product;
import com.albinos.ordering.domain.model.valueobject.Quantity;
import com.albinos.ordering.domain.model.valueobject.Shipping;
import com.albinos.ordering.domain.model.valueobject.id.CustomerId;

import java.util.Objects;

public class OrderFactory {

    private OrderFactory() {

    }

    public static Order filled(
            CustomerId customerId,
            Shipping shipping,
            Billing billing,
            PaymentMethod paymentMethod,
            Product product,
            Quantity productQuantity
    ) {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(shipping);
        Objects.requireNonNull(billing);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(product);
        Objects.requireNonNull(productQuantity);

        Order order = Order.draft(customerId);

        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, productQuantity);

        return order;
    }
}
