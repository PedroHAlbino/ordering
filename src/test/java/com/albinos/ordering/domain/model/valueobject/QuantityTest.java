package com.albinos.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void shouldGenerateWithValue(){
        Quantity quantity = new Quantity(10);

        Assertions.assertThat(quantity.value()).isEqualTo(10);
    }

    @Test
    void given_nullValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Quantity(null));
    }

    @Test
    void given_negativeValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(-1));
    }

    @Test
    void given_zeroValue_whenCreate_shouldNotGenerateException(){
        Assertions.assertThatCode(() -> new Quantity(0)).doesNotThrowAnyException();
    }

    @Test
    void shouldAddValues(){
        Quantity quantity = new Quantity(10);

        Quantity result = quantity.add(new Quantity(5));

        Assertions.assertThat(result).isEqualTo(new Quantity(15));
    }

    @Test
    void shouldCompareValues(){
        Assertions.assertThat(new Quantity(10).compareTo(new Quantity(5))).isPositive();
        Assertions.assertThat(new Quantity(5).compareTo(new Quantity(10))).isNegative();
        Assertions.assertThat(new Quantity(5).compareTo(new Quantity(5))).isZero();
    }

    @Test
    void shouldReturnValueOnToString(){
        Assertions.assertThat(new Quantity(10).toString()).isEqualTo("10");
    }

    @Test
    void shouldExposeZeroConstant(){
        Assertions.assertThat(Quantity.ZERO).isEqualTo(new Quantity(0));
    }

}
