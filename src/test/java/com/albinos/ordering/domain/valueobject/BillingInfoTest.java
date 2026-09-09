package com.albinos.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BillingInfoTest {

    @Test
    void shouldGenerateWithValidData(){
        BillingInfo billingInfo = BillingInfo.builder()
                .fullName(new FullName("Jhon", "Doe"))
                .document(new Document("255-08-0578"))
                .phone(new Phone("478-256-2504"))
                .address(newValidAddress())
                .build();

        Assertions.assertThat(billingInfo.fullName()).isEqualTo(new FullName("Jhon", "Doe"));
        Assertions.assertThat(billingInfo.document()).isEqualTo(new Document("255-08-0578"));
        Assertions.assertThat(billingInfo.phone()).isEqualTo(new Phone("478-256-2504"));
        Assertions.assertThat(billingInfo.address()).isEqualTo(newValidAddress());
    }

    @Test
    void given_nullFullName_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullDocument_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .phone(new Phone("478-256-2504"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullPhone_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .document(new Document("255-08-0578"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullAddress_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .build());
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
