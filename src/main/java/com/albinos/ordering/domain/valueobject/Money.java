package com.albinos.ordering.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.albinos.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MONEY_IS_NEGATIVE;
import static com.albinos.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_MUST_BE_AT_LEAST_ONE;

public record Money(BigDecimal value) implements Comparable<Money> {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value) {
        this(new BigDecimal(Objects.requireNonNull(value)));
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value);
        BigDecimal scaled = value.setScale(SCALE, ROUNDING_MODE);
        if (scaled.signum() < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_MONEY_IS_NEGATIVE);
        }
        this.value = scaled;
    }

    public Money multiply(Quantity quantity) {
        Objects.requireNonNull(quantity);
        if (quantity.value() < 1) {
            throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_MUST_BE_AT_LEAST_ONE);
        }
        return new Money(this.value.multiply(BigDecimal.valueOf(quantity.value())));
    }

    public Money add(Money other) {
        Objects.requireNonNull(other);
        return new Money(this.value.add(other.value));
    }

    public Money divide(Money other) {
        Objects.requireNonNull(other);
        return new Money(this.value.divide(other.value, SCALE, ROUNDING_MODE));
    }

    @Override
    public int compareTo(Money other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
