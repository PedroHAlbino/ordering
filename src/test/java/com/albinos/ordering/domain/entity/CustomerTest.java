package com.albinos.ordering.domain.entity;


import com.albinos.ordering.domain.exception.CustomerArchivedException;
import com.albinos.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(
                        IdGenerator.generateTimeBasedUUID(),
                        "invalid",
                        LocalDate.of(1991,7,5),
                        "Jhon Doe",
                        "255-08-0578",
                        "478-256-2504",
                        false,
                        OffsetDateTime.now()
                ));
    }

    @Test
    void given_invalidEmail_whenTryUpdateCustomerEmail_shouldGenerateException(){
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "jhon@email.com",
                LocalDate.of(1991,7,5),
                "Jhon Doe",
                "255-08-0578",
                "478-256-2504",
                false,
                OffsetDateTime.now()
        );
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.changeEmail("invalid");
                });
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnomize(){
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "jhon@email.com",
                LocalDate.of(1991,7,5),
                "Jhon Doe",
                "255-08-0578",
                "478-256-2504",
                false,
                OffsetDateTime.now()
        );

        customer.archive();

        Assertions.assertWith(customer,
            c-> Assertions.assertThat(c.fullName()).isEqualTo("Anonymous"),
            c->Assertions.assertThat(c.email()).isNotEqualTo("jhon@email.com"),
            c->Assertions.assertThat(c.phone()).isEqualTo("000-000-0000"),
            c->Assertions.assertThat(c.document()).isEqualTo("000-00-0000"),
                c->Assertions.assertThat(c.birthDate()).isNull()
        );

    }

    @Test
    void given_archivedCustomer_whenTryToArchive_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);
    }

    @Test
    void given_futureBirthDate_whenTryCreateCustomer_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(
                        IdGenerator.generateTimeBasedUUID(),
                        "jhon@email.com",
                        LocalDate.now().plusDays(1),
                        "Jhon Doe",
                        "255-08-0578",
                        "478-256-2504",
                        false,
                        OffsetDateTime.now()
                ));
    }

    @Test
    void given_blankFullName_whenTryCreateCustomer_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Customer(
                        IdGenerator.generateTimeBasedUUID(),
                        "jhon@email.com",
                        LocalDate.of(1991,7,5),
                        "   ",
                        "255-08-0578",
                        "478-256-2504",
                        false,
                        OffsetDateTime.now()
                ));
    }

    @Test
    void given_nullFullName_whenTryCreateCustomer_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Customer(
                        IdGenerator.generateTimeBasedUUID(),
                        "jhon@email.com",
                        LocalDate.of(1991,7,5),
                        null,
                        "255-08-0578",
                        "478-256-2504",
                        false,
                        OffsetDateTime.now()
                ));
    }

    @Test
    void given_validPoints_whenAddLoyaltyPoints_shouldIncreasePoints(){
        Customer customer = newValidCustomer();

        customer.addLoayltyPoints(10);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(10);
    }

    @Test
    void given_zeroOrNegativePoints_whenAddLoyaltyPoints_shouldGenerateException(){
        Customer customer = newValidCustomer();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoayltyPoints(0));
    }

    @Test
    void given_archivedCustomer_whenTryAddLoyaltyPoints_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.addLoayltyPoints(10));
    }

    @Test
    void given_archivedCustomer_whenTryChangeName_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeName("Other Name"));
    }

    @Test
    void given_archivedCustomer_whenTryChangePhone_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone("111-111-1111"));
    }

    @Test
    void given_archivedCustomer_whenTryChangeEmail_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail("other@email.com"));
    }

    @Test
    void given_archivedCustomer_whenTryEnablePromotionNotifications_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);
    }

    @Test
    void given_twoCustomersWithSameId_whenEquals_shouldBeEqual(){
        UUID id = IdGenerator.generateTimeBasedUUID();
        Customer customer1 = new Customer(
                id,
                "jhon@email.com",
                LocalDate.of(1991,7,5),
                "Jhon Doe",
                "255-08-0578",
                "478-256-2504",
                false,
                OffsetDateTime.now()
        );
        Customer customer2 = new Customer(
                id,
                "other@email.com",
                LocalDate.of(1980,1,1),
                "Other Name",
                "111-111-1111",
                "111-11-1111",
                true,
                OffsetDateTime.now()
        );

        Assertions.assertThat(customer1).isEqualTo(customer2);
        Assertions.assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }

    private Customer newValidCustomer(){
        return new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "jhon@email.com",
                LocalDate.of(1991,7,5),
                "Jhon Doe",
                "255-08-0578",
                "478-256-2504",
                false,
                OffsetDateTime.now()
        );
    }

    private Customer newArchivedCustomer(){
        return new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Anonymous",
                null,
                "jhon@email.com",
                "000-000-0000",
                "000-00-0000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                10
        );
    }

}