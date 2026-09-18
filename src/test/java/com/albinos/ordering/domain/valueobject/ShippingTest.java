package com.albinos.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ShippingTest {

    @Test
    void shouldGenerateWithValidData(){
        Shipping shipping = Shipping.builder()
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .recipient(newValidRecipient())
                .address(newValidAddress())
                .build();

        Assertions.assertThat(shipping.cost()).isEqualTo(new Money("10"));
        Assertions.assertThat(shipping.expectedDate()).isEqualTo(LocalDate.now().plusWeeks(1));
        Assertions.assertThat(shipping.recipient()).isEqualTo(newValidRecipient());
        Assertions.assertThat(shipping.address()).isEqualTo(newValidAddress());
    }

    @Test
    void given_nullCost_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Shipping.builder()
                        .expectedDate(LocalDate.now().plusWeeks(1))
                        .recipient(newValidRecipient())
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullExpectedDate_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Shipping.builder()
                        .cost(new Money("10"))
                        .recipient(newValidRecipient())
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullRecipient_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Shipping.builder()
                        .cost(new Money("10"))
                        .expectedDate(LocalDate.now().plusWeeks(1))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullAddress_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Shipping.builder()
                        .cost(new Money("10"))
                        .expectedDate(LocalDate.now().plusWeeks(1))
                        .recipient(newValidRecipient())
                        .build());
    }

    private Recipient newValidRecipient(){
        return Recipient.builder()
                .fullName(new FullName("Jhon", "Doe"))
                .document(new Document("255-08-0578"))
                .phone(new Phone("478-256-2504"))
                .build();
    }

    private Address newValidAddress(){
        return new Address(
                "Main Street",
                "102",
                "Apt 1",
                "Downtown",
                "Springfield",
                "IL",
                new ZipCode("12345")
        );
    }

}
