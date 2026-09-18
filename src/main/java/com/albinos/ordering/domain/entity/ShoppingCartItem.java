package com.albinos.ordering.domain.entity;

import com.albinos.ordering.domain.exception.ShoppingCartItemIncompatibleProductException;
import com.albinos.ordering.domain.valueobject.Money;
import com.albinos.ordering.domain.valueobject.Product;
import com.albinos.ordering.domain.valueobject.ProductName;
import com.albinos.ordering.domain.valueobject.Quantity;
import com.albinos.ordering.domain.valueobject.id.ProductId;
import com.albinos.ordering.domain.valueobject.id.ShoppingCartItemId;
import lombok.Builder;

import java.util.Objects;

import static com.albinos.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_MUST_BE_AT_LEAST_ONE;

public class ShoppingCartItem {

    private ShoppingCartItemId id;

    private ProductId productId;
    private ProductName productName;

    private Money price;
    private Quantity quantity;

    private Money totalAmount;

    private Boolean available;

    @Builder(builderClassName = "ExistingShoppingCartItemBuilder", builderMethodName = "existing")
    public ShoppingCartItem(ShoppingCartItemId id, ProductId productId, ProductName productName,
                             Money price, Quantity quantity, Money totalAmount, Boolean available) {
        this.setId(id);
        this.setProductId(productId);
        this.setProductName(productName);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setTotalAmount(totalAmount);
        this.setAvailable(available);
    }

    @Builder(builderClassName = "BrandNewShoppingCartItemBuilder", builderMethodName = "brandNew")
    static ShoppingCartItem createBrandNew(Product product, Quantity quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        ShoppingCartItem item = new ShoppingCartItem(
                new ShoppingCartItemId(),
                product.id(),
                product.name(),
                product.price(),
                quantity,
                Money.ZERO,
                product.inStock()
        );
        item.recalculateTotals();
        return item;
    }

    public ShoppingCartItemId id() {
        return id;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Boolean available() {
        return available;
    }

    public void refresh(Product product) {
        Objects.requireNonNull(product);
        this.verifyProductCompatibility(product);

        this.setPrice(product.price());
        this.setProductName(product.name());
        this.setAvailable(product.inStock());

        this.recalculateTotals();
    }

    public void changeQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        if (quantity.value() <= 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_MUST_BE_AT_LEAST_ONE);
        }

        this.setQuantity(quantity);
        this.recalculateTotals();
    }

    private void verifyProductCompatibility(Product product) {
        if (!this.productId().equals(product.id())) {
            throw new ShoppingCartItemIncompatibleProductException(this.id(), product.id());
        }
    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price().multiply(this.quantity()));
    }

    private void setId(ShoppingCartItemId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setProductId(ProductId productId) {
        Objects.requireNonNull(productId);
        this.productId = productId;
    }

    private void setProductName(ProductName productName) {
        Objects.requireNonNull(productName);
        this.productName = productName;
    }

    private void setPrice(Money price) {
        Objects.requireNonNull(price);
        this.price = price;
    }

    private void setQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setAvailable(Boolean available) {
        Objects.requireNonNull(available);
        this.available = available;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ShoppingCartItem that = (ShoppingCartItem) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
