package com.albinos.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class MoneyTest {

    @Test
    void shouldGenerateWithBigDecimalValue(){
        Money money = new Money(BigDecimal.TEN);

        Assertions.assertThat(money.value()).isEqualByComparingTo("10.00");
    }

    @Test
    void shouldGenerateWithStringValue(){
        Money money = new Money("10.5");

        Assertions.assertThat(money.value()).isEqualByComparingTo("10.50");
    }

    @Test
    void shouldRoundWithHalfEven(){
        Money money = new Money(new BigDecimal("10.125"));

        Assertions.assertThat(money.value()).isEqualByComparingTo("10.12");
    }

    @Test
    void given_nullBigDecimalValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money((BigDecimal) null));
    }

    @Test
    void given_nullStringValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money((String) null));
    }

    @Test
    void given_negativeValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("-0.01")));
    }

    @Test
    void shouldAddValues(){
        Money result = new Money("10.00").add(new Money("5.50"));

        Assertions.assertThat(result).isEqualTo(new Money("15.50"));
    }

    @Test
    void shouldMultiplyByQuantity(){
        Money result = new Money("10.00").multiply(new Quantity(3));

        Assertions.assertThat(result).isEqualTo(new Money("30.00"));
    }

    @Test
    void given_quantityLessThanOne_whenMultiply_shouldGenerateException(){
        Money money = new Money("10.00");

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> money.multiply(Quantity.ZERO));
    }

    @Test
    void shouldDivideValues(){
        Money result = new Money("10.00").divide(new Money("2.00"));

        Assertions.assertThat(result).isEqualTo(new Money("5.00"));
    }

    @Test
    void shouldCompareValues(){
        Assertions.assertThat(new Money("10.00").compareTo(new Money("5.00"))).isPositive();
        Assertions.assertThat(new Money("5.00").compareTo(new Money("10.00"))).isNegative();
        Assertions.assertThat(new Money("5.00").compareTo(new Money("5.00"))).isZero();
    }

    @Test
    void shouldReturnValueOnToString(){
        Assertions.assertThat(new Money("10.00").toString()).isEqualTo("10.00");
    }

    @Test
    void shouldExposeZeroConstant(){
        Assertions.assertThat(Money.ZERO).isEqualTo(new Money(BigDecimal.ZERO));
    }

}
