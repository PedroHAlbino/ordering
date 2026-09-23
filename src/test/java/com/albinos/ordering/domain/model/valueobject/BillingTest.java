package com.albinos.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BillingTest {

    @Test
    void shouldGenerateWithValidData(){
        Billing billing = Billing.builder()
                .fullName(new FullName("Jhon", "Doe"))
                .document(new Document("255-08-0578"))
                .phone(new Phone("478-256-2504"))
                .email(new Email("jhon.doe@gmail.com"))
                .address(newValidAddress())
                .build();

        Assertions.assertThat(billing.fullName()).isEqualTo(new FullName("Jhon", "Doe"));
        Assertions.assertThat(billing.document()).isEqualTo(new Document("255-08-0578"));
        Assertions.assertThat(billing.phone()).isEqualTo(new Phone("478-256-2504"));
        Assertions.assertThat(billing.email()).isEqualTo(new Email("jhon.doe@gmail.com"));
        Assertions.assertThat(billing.address()).isEqualTo(newValidAddress());
    }

    @Test
    void given_nullFullName_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .email(new Email("jhon.doe@gmail.com"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullDocument_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .phone(new Phone("478-256-2504"))
                        .email(new Email("jhon.doe@gmail.com"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullPhone_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .document(new Document("255-08-0578"))
                        .email(new Email("jhon.doe@gmail.com"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullEmail_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_nullAddress_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("Jhon", "Doe"))
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .email(new Email("jhon.doe@gmail.com"))
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
