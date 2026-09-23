package com.albinos.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void shouldGenerateWithValue(){
        Phone phone = new Phone("478-256-2504");

        Assertions.assertThat(phone.value()).isEqualTo("478-256-2504");
    }

    @Test
    void given_nullValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Phone(null));
    }

    @Test
    void given_blankValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Phone("   "));
    }

    @Test
    void shouldReturnValueOnToString(){
        Phone phone = new Phone("478-256-2504");

        Assertions.assertThat(phone.toString()).isEqualTo("478-256-2504");
    }

}
