package com.albinos.ordering.domain.model.entity;

import com.albinos.ordering.domain.model.exception.ShoppingCartItemIncompatibleProductException;
import com.albinos.ordering.domain.model.valueobject.Money;
import com.albinos.ordering.domain.model.valueobject.Product;
import com.albinos.ordering.domain.model.valueobject.ProductName;
import com.albinos.ordering.domain.model.valueobject.Quantity;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

class ShoppingCartItemTest {

    @Test
    public void givenNewProduct_whenBrandNew_shouldCalculateTotalAmount() {
        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(3);

        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .product(product)
                .quantity(quantity)
                .build();

        Assertions.assertWith(item,
                i -> Assertions.assertThat(i.id()).isNotNull(),
                i -> Assertions.assertThat(i.productId()).isEqualTo(product.id()),
                i -> Assertions.assertThat(i.productName()).isEqualTo(product.name()),
                i -> Assertions.assertThat(i.price()).isEqualTo(product.price()),
                i -> Assertions.assertThat(i.quantity()).isEqualTo(quantity),
                i -> Assertions.assertThat(i.available()).isEqualTo(product.inStock()),
                i -> Assertions.assertThat(i.totalAmount()).isEqualTo(product.price().multiply(quantity))
        );
    }

    @Test
    public void givenItem_whenChangeQuantityToZero_shouldGenerateException() {
        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .product(ProductTestDataBuilder.aProduct().build())
                .quantity(new Quantity(1))
                .build();

        ThrowableAssert.ThrowingCallable task = () -> item.changeQuantity(Quantity.ZERO);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(task);
    }

    @Test
    public void givenItem_whenChangeQuantity_shouldRecalculateTotalAmount() {
        Product product = ProductTestDataBuilder.aProduct().build();
        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .product(product)
                .quantity(new Quantity(1))
                .build();

        item.changeQuantity(new Quantity(4));

        Assertions.assertThat(item.quantity()).isEqualTo(new Quantity(4));
        Assertions.assertThat(item.totalAmount()).isEqualTo(product.price().multiply(new Quantity(4)));
    }

    @Test
    public void givenItem_whenRefreshWithCompatibleProduct_shouldUpdateData() {
        Product product = ProductTestDataBuilder.aProduct().build();
        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .product(product)
                .quantity(new Quantity(2))
                .build();

        Product refreshedProduct = Product.builder()
                .id(product.id())
                .name(new ProductName("Notebook X11 Pro"))
                .price(new Money("3500"))
                .inStock(false)
                .build();

        item.refresh(refreshedProduct);

        Assertions.assertThat(item.price()).isEqualTo(refreshedProduct.price());
        Assertions.assertThat(item.productName()).isEqualTo(refreshedProduct.name());
        Assertions.assertThat(item.available()).isFalse();
        Assertions.assertThat(item.totalAmount()).isEqualTo(refreshedProduct.price().multiply(item.quantity()));
    }

    @Test
    public void givenItem_whenRefreshWithIncompatibleProduct_shouldGenerateException() {
        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .product(ProductTestDataBuilder.aProduct().build())
                .quantity(new Quantity(1))
                .build();

        Product incompatibleProduct = ProductTestDataBuilder.aProductAltMousePad().build();

        ThrowableAssert.ThrowingCallable task = () -> item.refresh(incompatibleProduct);

        Assertions.assertThatExceptionOfType(ShoppingCartItemIncompatibleProductException.class).isThrownBy(task);
    }

    @Test
    public void givenTwoItemsWithSameId_whenCompared_shouldBeEqual() {
        Product product = ProductTestDataBuilder.aProduct().build();
        ShoppingCartItem item = ShoppingCartItem.brandNew()
                .product(product)
                .quantity(new Quantity(1))
                .build();

        ShoppingCartItem sameItem = ShoppingCartItem.existing()
                .id(item.id())
                .productId(product.id())
                .productName(product.name())
                .price(product.price())
                .quantity(new Quantity(5))
                .totalAmount(product.price().multiply(new Quantity(5)))
                .available(true)
                .build();

        Assertions.assertThat(item).isEqualTo(sameItem);
    }
}
