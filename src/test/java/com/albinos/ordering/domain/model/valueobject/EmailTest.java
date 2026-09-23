package com.albinos.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void shouldGenerateWithValue(){
        Email email = new Email("jhon.doe@email.com");

        Assertions.assertThat(email.value()).isEqualTo("jhon.doe@email.com");
    }

    @Test
    void given_nullValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Email(null));
    }

    @Test
    void given_blankValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("   "));
    }

    @Test
    void given_invalidFormat_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("invalid"));
    }

    @Test
    void shouldReturnValueOnToString(){
        Email email = new Email("jhon.doe@email.com");

        Assertions.assertThat(email.toString()).isEqualTo("jhon.doe@email.com");
    }

}
