package com.albinos.ordering.domain.exception;

import com.albinos.ordering.domain.valueobject.id.ProductId;
import com.albinos.ordering.domain.valueobject.id.ShoppingCartItemId;

import static com.albinos.ordering.domain.exception.ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT;

public class ShoppingCartItemIncompatibleProductException extends DomainException {

    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}
