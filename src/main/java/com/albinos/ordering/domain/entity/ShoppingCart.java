package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.ShoppingCartDoesNotContainItemException;
import com.albinos.ordering.domain.valueobject.Money;
import com.albinos.ordering.domain.valueobject.Product;
import com.albinos.ordering.domain.valueobject.Quantity;
import com.albinos.ordering.domain.valueobject.id.CustomerId;
import com.albinos.ordering.domain.valueobject.id.ProductId;
import com.albinos.ordering.domain.valueobject.id.ShoppingCartId;
import com.albinos.ordering.domain.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ShoppingCart {

    private ShoppingCartId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;

    private OffsetDateTime createdAt;

    private Set<ShoppingCartItem> items;

    @Builder(builderClassName = "ExistingShoppingCartBuilder", builderMethodName = "existing")
    public ShoppingCart(ShoppingCartId id, CustomerId customerId, Money totalAmount,
                         Quantity totalItems, OffsetDateTime createdAt, Set<ShoppingCartItem> items) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setCreatedAt(createdAt);
        this.setItems(items);
    }

    public static ShoppingCart startShopping(CustomerId customerId) {
        return new ShoppingCart(
                new ShoppingCartId(),
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                OffsetDateTime.now(),
                new HashSet<>()
        );
    }

    public void addItem(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        product.checkOutOfStock();

        Optional<ShoppingCartItem> existingItem = this.findItemByProductId(product.id());

        if (existingItem.isPresent()) {
            ShoppingCartItem item = existingItem.get();
            item.refresh(product);
            item.changeQuantity(item.quantity().add(quantity));
        } else {
            ShoppingCartItem newItem = ShoppingCartItem.brandNew()
                    .product(product)
                    .quantity(quantity)
                    .build();

            if (this.items == null) {
                this.items = new HashSet<>();
            }

            this.items.add(newItem);
        }

        this.recalculateTotals();
    }

    public void removeItem(ShoppingCartItemId itemId) {
        Objects.requireNonNull(itemId);

        ShoppingCartItem item = this.findItem(itemId);
        this.items.remove(item);

        this.recalculateTotals();
    }

    public void refreshItem(Product product) {
        Objects.requireNonNull(product);

        ShoppingCartItem item = this.findItem(product.id());
        item.refresh(product);

        this.recalculateTotals();
    }

    public void changeItemQuantity(ShoppingCartItemId itemId, Quantity quantity) {
        Objects.requireNonNull(itemId);
        Objects.requireNonNull(quantity);

        ShoppingCartItem item = this.findItem(itemId);
        item.changeQuantity(quantity);

        this.recalculateTotals();
    }

    public void empty() {
        Set<ShoppingCartItem> itemsToRemove = new HashSet<>(this.items());
        itemsToRemove.forEach(item -> this.items.remove(item));

        this.recalculateTotals();
    }

    public ShoppingCartItem findItem(ShoppingCartItemId itemId) {
        Objects.requireNonNull(itemId);
        return this.items().stream()
                .filter(i -> i.id().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id(), itemId));
    }

    public ShoppingCartItem findItem(ProductId productId) {
        Objects.requireNonNull(productId);
        return this.findItemByProductId(productId)
                .orElseThrow(() -> new ShoppingCartDoesNotContainItemException(this.id(), productId));
    }

    private Optional<ShoppingCartItem> findItemByProductId(ProductId productId) {
        return this.items().stream()
                .filter(i -> i.productId().equals(productId))
                .findFirst();
    }

    private void recalculateTotals() {
        BigDecimal totalAmount = this.items().stream()
                .map(i -> i.totalAmount().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items().stream()
                .map(i -> i.quantity().value())
                .reduce(0, Integer::sum);

        this.setTotalAmount(new Money(totalAmount));
        this.setTotalItems(new Quantity(totalItemsQuantity));
    }

    public boolean containsUnavailableItems() {
        return this.items().stream().anyMatch(i -> !i.available());
    }

    public boolean isEmpty() {
        return this.items().isEmpty();
    }

    public ShoppingCartId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public Set<ShoppingCartItem> items() {
        return Collections.unmodifiableSet(this.items);
    }

    private void setId(ShoppingCartId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItems) {
        Objects.requireNonNull(totalItems);
        this.totalItems = totalItems;
    }

    private void setCreatedAt(OffsetDateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
    }

    private void setItems(Set<ShoppingCartItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ShoppingCart shoppingCart = (ShoppingCart) object;
        return Objects.equals(id, shoppingCart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
