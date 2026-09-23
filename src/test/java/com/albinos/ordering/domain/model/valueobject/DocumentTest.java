package com.albinos.ordering.domain.model.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void shouldGenerateWithValue(){
        Document document = new Document("255-08-0578");

        Assertions.assertThat(document.value()).isEqualTo("255-08-0578");
    }

    @Test
    void given_nullValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Document(null));
    }

    @Test
    void given_blankValue_whenCreate_shouldGenerateException(){
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document("   "));
    }

    @Test
    void shouldReturnValueOnToString(){
        Document document = new Document("255-08-0578");

        Assertions.assertThat(document.toString()).isEqualTo("255-08-0578");
    }

}
