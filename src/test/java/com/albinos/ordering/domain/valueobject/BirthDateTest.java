package com.albinos.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class BirthDateTest {

    @Test
    void shouldGenerateWithValue(){
        LocalDate value = LocalDate.of(1991, 7, 5);
        BirthDate birthDate = new BirthDate(value);

        Assertions.assertThat(birthDate.value()).isEqualTo(value);
    }

    @Test
    void given_nullValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BirthDate(null));
    }

    @Test
    void given_futureDate_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void shouldCalculateAge(){
        BirthDate birthDate = new BirthDate(LocalDate.now().minusYears(25));

        Assertions.assertThat(birthDate.age()).isEqualTo(25);
    }

    @Test
    void shouldReturnValueOnToString(){
        LocalDate value = LocalDate.of(1991, 7, 5);
        BirthDate birthDate = new BirthDate(value);

        Assertions.assertThat(birthDate.toString()).isEqualTo(value.toString());
    }

}
