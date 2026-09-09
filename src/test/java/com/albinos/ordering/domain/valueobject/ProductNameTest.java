package com.albinos.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @Test
    void shouldGenerateWithValue(){
        ProductName productName = new ProductName("Notebook");

        Assertions.assertThat(productName.value()).isEqualTo("Notebook");
    }

    @Test
    void given_nullValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new ProductName(null));
    }

    @Test
    void given_blankValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName("   "));
    }

    @Test
    void shouldReturnValueOnToString(){
        Assertions.assertThat(new ProductName("Notebook").toString()).isEqualTo("Notebook");
    }

}
