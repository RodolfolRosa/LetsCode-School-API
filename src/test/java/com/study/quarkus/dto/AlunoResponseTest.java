package com.study.quarkus.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoResponseTest {

    private static final int ID = 100;
    private static final String NAME = "SOME NAME";
    private static final String TUTOR = "SOME TUTOR";
    private static final String DATETIME = "SOME DATE TIME";


    @Test
    void constructorBuilder() {

        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var response =
                AlunoResponse.builder()
                        .id(ID)
                        .nome(NAME)
                        .tutor(TUTOR)
                        .dateTime(DATETIME)
                        .build();

        //THEN
        assertFields(validator, response);
        factory.close();
    }

    @Test
    void constructorAllArgs() {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var response = new AlunoResponse(ID, NAME, TUTOR, DATETIME);

        //THEN
        assertFields(validator, response);
        factory.close();
    }

    @Test
    void constructorDefault() {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var response = new AlunoResponse();
        response.setId(ID);
        response.setNome(NAME);
        response.setTutor(TUTOR);
        response.setDateTime(DATETIME);

        //THEN
        assertFields(validator, response);
        factory.close();
    }

    @Test
    void equalsAndHashcodeContract() {
        EqualsVerifier.simple().forClass(AlunoResponse.class)
                .verify();
    }

    private void assertFields(final Validator validator, final AlunoResponse response) {
        final var violations = validator.validate(response);
        assertThat(violations).isEmpty();

        assertThat(response.getNome()).isEqualTo(NAME);
    }
    
}