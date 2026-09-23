package com.albinos.ordering.domain.model.valueobject;



import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void shouldGenerateWithValue(){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldAddValue(){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        LoyaltyPoints result = loyaltyPoints.add(5);
        Assertions.assertThat(result.value()).isEqualTo(15);
    }

}