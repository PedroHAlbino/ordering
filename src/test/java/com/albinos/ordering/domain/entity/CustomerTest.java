package com.albinos.ordering.domain.entity;


import com.albinos.ordering.domain.exception.CustomerArchivedException;
import com.albinos.ordering.domain.valueobject.Address;
import com.albinos.ordering.domain.valueobject.BirthDate;
import com.albinos.ordering.domain.valueobject.id.CustomerId;
import com.albinos.ordering.domain.valueobject.Document;
import com.albinos.ordering.domain.valueobject.Email;
import com.albinos.ordering.domain.valueobject.FullName;
import com.albinos.ordering.domain.valueobject.LoyaltyPoints;
import com.albinos.ordering.domain.valueobject.Phone;
import com.albinos.ordering.domain.valueobject.ZipCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("invalid"));
    }

    @Test
    void given_invalidEmail_whenTryUpdateCustomerEmail_shouldGenerateException(){
        Customer customer = newValidCustomer();
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("invalid")));
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnomize(){
        Customer customer = newValidCustomer();

        customer.archive();

        Assertions.assertWith(customer,
            c-> Assertions.assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
            c->Assertions.assertThat(c.email()).isNotEqualTo(new Email("jhon@email.com")),
            c->Assertions.assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
            c->Assertions.assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
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
                .isThrownBy(() -> new BirthDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void given_nullBirthDate_whenTryCreateBirthDate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BirthDate(null));
    }

    @Test
    void given_pastBirthDate_whenAge_shouldCalculateCorrectly(){
        BirthDate birthDate = new BirthDate(LocalDate.now().minusYears(30));

        Assertions.assertThat(birthDate.age()).isEqualTo(30);
    }

    @Test
    void given_blankDocument_whenTryCreateDocument_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document("   "));
    }

    @Test
    void given_nullDocument_whenTryCreateDocument_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Document(null));
    }

    @Test
    void given_blankPhone_whenTryCreatePhone_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Phone("   "));
    }

    @Test
    void given_nullPhone_whenTryCreatePhone_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Phone(null));
    }

    @Test
    void given_blankFirstName_whenTryCreateFullName_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new FullName("   ", "Doe"));
    }

    @Test
    void given_nullFullName_whenTryCreateCustomer_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Customer.brandNew()
                        .id(new CustomerId())
                        .email(new Email("jhon@email.com"))
                        .birthDate(new BirthDate(LocalDate.of(1991,7,5)))
                        .fullName(null)
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .promotionNotificationsAllowed(false)
                        .address(newValidAddress())
                        .build());
    }

    @Test
    void given_validPoints_whenAddLoyaltyPoints_shouldIncreasePoints(){
        Customer customer = newValidCustomer();

        customer.addLoayltyPoints(10);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(10));
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
                .isThrownBy(() -> customer.changeName(new FullName("Other", "Name")));
    }

    @Test
    void given_archivedCustomer_whenTryChangePhone_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone(new Phone("111-111-1111")));
    }

    @Test
    void given_archivedCustomer_whenTryChangeEmail_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("other@email.com")));
    }

    @Test
    void given_archivedCustomer_whenTryEnablePromotionNotifications_shouldGenerateException(){
        Customer customer = newArchivedCustomer();

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);
    }

    @Test
    void given_twoCustomersWithSameId_whenEquals_shouldBeEqual(){
        CustomerId id = new CustomerId();
        Customer customer1 = Customer.brandNew()
                .id(id)
                .email(new Email("jhon@email.com"))
                .birthDate(new BirthDate(LocalDate.of(1991,7,5)))
                .fullName(new FullName("Jhon", "Doe"))
                .document(new Document("255-08-0578"))
                .phone(new Phone("478-256-2504"))
                .promotionNotificationsAllowed(false)
                .address(newValidAddress())
                .build();
        Customer customer2 = Customer.brandNew()
                .id(id)
                .email(new Email("other@email.com"))
                .birthDate(new BirthDate(LocalDate.of(1980,1,1)))
                .fullName(new FullName("Other", "Name"))
                .document(new Document("111-111-1111"))
                .phone(new Phone("111-11-1111"))
                .promotionNotificationsAllowed(true)
                .address(newValidAddress())
                .build();

        Assertions.assertThat(customer1).isEqualTo(customer2);
        Assertions.assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }

    private Customer newValidCustomer(){
        return Customer.brandNew()
                .id(new CustomerId())
                .email(new Email("jhon@email.com"))
                .birthDate(new BirthDate(LocalDate.of(1991,7,5)))
                .fullName(new FullName("Jhon", "Doe"))
                .document(new Document("255-08-0578"))
                .phone(new Phone("478-256-2504"))
                .promotionNotificationsAllowed(false)
                .address(newValidAddress())
                .build();
    }

    private Customer newArchivedCustomer(){
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(null)
                .email(new Email("jhon@email.com"))
                .phone(new Phone("000-000-0000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltyPoints(10))
                .address(newValidAddress())
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
