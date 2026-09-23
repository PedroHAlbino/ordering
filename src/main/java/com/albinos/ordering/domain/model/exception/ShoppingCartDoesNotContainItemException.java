package com.albinos.ordering.domain.model.exception;

import com.albinos.ordering.domain.model.valueobject.id.ProductId;
import com.albinos.ordering.domain.model.valueobject.id.ShoppingCartId;
import com.albinos.ordering.domain.model.valueobject.id.ShoppingCartItemId;

import static com.albinos.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM;

public class ShoppingCartDoesNotContainItemException extends DomainException {

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ShoppingCartItemId itemId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, itemId));
    }

    public ShoppingCartDoesNotContainItemException(ShoppingCartId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM, id, productId));
    }
}
