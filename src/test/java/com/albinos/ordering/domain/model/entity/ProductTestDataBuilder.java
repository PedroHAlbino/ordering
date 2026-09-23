package com.albinos.ordering.domain.model.entity;

import com.albinos.ordering.domain.model.valueobject.Money;
import com.albinos.ordering.domain.model.valueobject.Product;
import com.albinos.ordering.domain.model.valueobject.ProductName;
import com.albinos.ordering.domain.model.valueobject.id.ProductId;

public class ProductTestDataBuilder {
    private ProductTestDataBuilder() {
    }

    public static final ProductId PRODUCT_ID = new ProductId();
    public static final ProductId PRODUCT_UNAVAILABLE_ID = new ProductId();
    public static final ProductId PRODUCT_ALT_RAM_MEMORY_ID = new ProductId();
    public static final ProductId PRODUCT_ALT_MOUSE_PAD_ID = new ProductId();

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(PRODUCT_ID)
                .inStock(true)
                .name(new ProductName("Notebook X11"))
                .price(new Money("3000"));
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .id(PRODUCT_UNAVAILABLE_ID)
                .name(new ProductName("Desktop FX9000"))
                .price(new Money("5000"))
                .inStock(false);
    }

    public static Product.ProductBuilder aProductAltRamMemory() {
        return Product.builder()
                .id(PRODUCT_ALT_RAM_MEMORY_ID)
                .name(new ProductName("4GB RAM"))
                .price(new Money("200"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductAltMousePad() {
        return Product.builder()
                .id(PRODUCT_ALT_MOUSE_PAD_ID)
                .name(new ProductName("Mouse Pad"))
                .price(new Money("100"))
                .inStock(true);
    }
}
