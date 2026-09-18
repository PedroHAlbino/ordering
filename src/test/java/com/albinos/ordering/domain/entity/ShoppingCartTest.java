package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.ProductOutOfStockException;
import com.albinos.ordering.domain.exception.ShoppingCartDoesNotContainItemException;
import com.albinos.ordering.domain.exception.ShoppingCartItemIncompatibleProductException;
import com.albinos.ordering.domain.valueobject.Money;
import com.albinos.ordering.domain.valueobject.Product;
import com.albinos.ordering.domain.valueobject.ProductName;
import com.albinos.ordering.domain.valueobject.Quantity;
import com.albinos.ordering.domain.valueobject.id.CustomerId;
import com.albinos.ordering.domain.valueobject.id.ShoppingCartItemId;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class ShoppingCartTest {

    @Test
    public void whenStartShopping_shouldCreateEmptyCart() {
        CustomerId customerId = new CustomerId();

        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        Assertions.assertWith(cart,
                c -> Assertions.assertThat(c.id()).isNotNull(),
                c -> Assertions.assertThat(c.customerId()).isEqualTo(customerId),
                c -> Assertions.assertThat(c.totalAmount()).isEqualTo(Money.ZERO),
                c -> Assertions.assertThat(c.totalItems()).isEqualTo(Quantity.ZERO),
                c -> Assertions.assertThat(c.createdAt()).isNotNull(),
                c -> Assertions.assertThat(c.isEmpty()).isTrue()
        );
    }

    @Test
    public void givenOutOfStockProduct_whenAddItem_shouldGenerateException() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        Product product = ProductTestDataBuilder.aProductUnavailable().build();

        ThrowableAssert.ThrowingCallable task = () -> cart.addItem(product, new Quantity(1));

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class).isThrownBy(task);
    }

    @Test
    public void givenSameProductAddedTwice_whenAddItem_shouldIncrementQuantityAndUpdateData() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(1));

        Product updatedProduct = Product.builder()
                .id(product.id())
                .name(new ProductName("Notebook X11 Pro"))
                .price(new Money("3500"))
                .inStock(true)
                .build();

        cart.addItem(updatedProduct, new Quantity(2));

        Assertions.assertThat(cart.items()).hasSize(1);

        ShoppingCartItem item = cart.items().iterator().next();
        Assertions.assertThat(item.quantity()).isEqualTo(new Quantity(3));
        Assertions.assertThat(item.price()).isEqualTo(updatedProduct.price());
        Assertions.assertThat(item.productName()).isEqualTo(updatedProduct.name());
        Assertions.assertThat(cart.totalItems()).isEqualTo(new Quantity(3));
        Assertions.assertThat(cart.totalAmount()).isEqualTo(updatedProduct.price().multiply(new Quantity(3)));
    }

    @Test
    public void givenDifferentProducts_whenAddItem_shouldAddTwoDistinctItems() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

        cart.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1));
        cart.addItem(ProductTestDataBuilder.aProductAltMousePad().build(), new Quantity(2));

        Assertions.assertThat(cart.items()).hasSize(2);
        Assertions.assertThat(cart.totalItems()).isEqualTo(new Quantity(3));
        Assertions.assertThat(cart.totalAmount())
                .isEqualTo(new Money("3000").multiply(new Quantity(1)).add(new Money("100").multiply(new Quantity(2))));
    }

    @Test
    public void givenNonExistentItem_whenRemoveItem_shouldGenerateException() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());

        ThrowableAssert.ThrowingCallable task = () -> cart.removeItem(new ShoppingCartItemId());

        Assertions.assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class).isThrownBy(task);
    }

    @Test
    public void givenCartWithItem_whenRemoveItem_shouldRemoveAndRecalculateTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        cart.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1));
        ShoppingCartItem item = cart.items().iterator().next();

        cart.removeItem(item.id());

        Assertions.assertThat(cart.isEmpty()).isTrue();
        Assertions.assertThat(cart.totalAmount()).isEqualTo(Money.ZERO);
        Assertions.assertThat(cart.totalItems()).isEqualTo(Quantity.ZERO);
    }

    @Test
    public void givenCartWithItems_whenEmpty_shouldRemoveAllItemsAndZeroTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        cart.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1));
        cart.addItem(ProductTestDataBuilder.aProductAltMousePad().build(), new Quantity(2));

        cart.empty();

        Assertions.assertThat(cart.isEmpty()).isTrue();
        Assertions.assertThat(cart.totalAmount()).isEqualTo(Money.ZERO);
        Assertions.assertThat(cart.totalItems()).isEqualTo(Quantity.ZERO);
    }

    @Test
    public void givenCartWithItem_whenRefreshItem_shouldUpdateDataAndRecalculateTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();
        cart.addItem(product, new Quantity(1));

        Product updatedProduct = Product.builder()
                .id(product.id())
                .name(new ProductName("Notebook X11 Pro"))
                .price(new Money("3500"))
                .inStock(false)
                .build();

        cart.refreshItem(updatedProduct);

        ShoppingCartItem item = cart.items().iterator().next();
        Assertions.assertThat(item.price()).isEqualTo(updatedProduct.price());
        Assertions.assertThat(item.productName()).isEqualTo(updatedProduct.name());
        Assertions.assertThat(item.available()).isFalse();
        Assertions.assertThat(cart.totalAmount()).isEqualTo(updatedProduct.price().multiply(new Quantity(1)));
        Assertions.assertThat(cart.containsUnavailableItems()).isTrue();
    }

    @Test
    public void givenCartItem_whenRefreshWithIncompatibleProduct_shouldGenerateException() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        cart.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1));
        ShoppingCartItem item = cart.items().iterator().next();

        Product incompatibleProduct = ProductTestDataBuilder.aProductAltMousePad().build();

        ThrowableAssert.ThrowingCallable task = () -> item.refresh(incompatibleProduct);

        Assertions.assertThatExceptionOfType(ShoppingCartItemIncompatibleProductException.class).isThrownBy(task);
    }

    @Test
    public void givenCartWithItem_whenChangeItemQuantity_shouldRecalculateTotals() {
        ShoppingCart cart = ShoppingCart.startShopping(new CustomerId());
        Product product = ProductTestDataBuilder.aProduct().build();
        cart.addItem(product, new Quantity(1));
        ShoppingCartItem item = cart.items().iterator().next();

        cart.changeItemQuantity(item.id(), new Quantity(5));

        Assertions.assertThat(item.quantity()).isEqualTo(new Quantity(5));
        Assertions.assertThat(cart.totalItems()).isEqualTo(new Quantity(5));
        Assertions.assertThat(cart.totalAmount()).isEqualTo(product.price().multiply(new Quantity(5)));
    }

    @Test
    public void givenTwoCartsWithSameId_whenCompared_shouldBeEqual() {
        CustomerId customerId = new CustomerId();
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        ShoppingCart sameCart = ShoppingCart.existing()
                .id(cart.id())
                .customerId(customerId)
                .totalAmount(Money.ZERO)
                .totalItems(Quantity.ZERO)
                .createdAt(cart.createdAt())
                .items(new HashSet<>())
                .build();

        Assertions.assertThat(cart).isEqualTo(sameCart);
    }
}
