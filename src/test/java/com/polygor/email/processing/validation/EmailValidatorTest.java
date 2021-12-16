package com.polygor.email.processing.validation;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {

    private final EmailValidator emailValidator = new EmailValidator();

    @Test
    public void shouldValidateComenonEmail(){
        //GIVEN
        String email = "user@comenon.com";
        //WHEN
        boolean valid = emailValidator.validate(email);
        //THEN
        assertThat("Email is valid", valid, is(true));
    }

    @Test
    public void shouldValidateCherryEmail(){
        //GIVEN
        String email = "user@cherry.se";
        //WHEN
        boolean valid = emailValidator.validate(email);
        //THEN
        assertThat("Email is valid", valid, is(true));
    }

    @Test
    public void shouldNotValidateEmail(){
        //GIVEN
        String email = "user@cherry.de";
        //WHEN
        boolean valid = emailValidator.validate(email);
        //THEN
        assertThat("Email is invalid", valid, is(false));
    }

    @Test
    public void shouldNotValidateUrl(){
        //GIVEN
        String email = "blabla.cherry.se";
        //WHEN
        boolean valid = emailValidator.validate(email);
        //THEN
        assertThat("Email is invalid", valid, is(false));
    }


}
