package com.albinos.ordering.domain.entity;


import com.albinos.ordering.domain.valueobject.Money;
import com.albinos.ordering.domain.valueobject.ProductName;
import com.albinos.ordering.domain.valueobject.Quantity;
import com.albinos.ordering.domain.valueobject.id.OrderId;
import com.albinos.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerate(){
        OrderItem.brandNew()
                .productId(new ProductId())
                .quantity(new Quantity(1))
                .orderId(new OrderId())
                .productName(new ProductName("Mouse pad"))
                .price(new Money("100")).build();
    }

}