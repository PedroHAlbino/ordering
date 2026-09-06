package com.albinos.ordering;

import com.albinos.ordering.domain.entity.Customer;
import com.albinos.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTest {

    @Test
    public void testingCustomer(){
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "jhon.doe@gmail.com",
                LocalDate.of(1991, 7, 5),
                "Jhon Doe",
                "123.456.789-00",
                "255-08-0578",
                true,
                OffsetDateTime.now()
        );

        customer.addLoayltyPoints(10);
    }
}
