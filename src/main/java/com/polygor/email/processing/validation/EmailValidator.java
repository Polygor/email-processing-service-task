package com.polygor.email.processing.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9_.+-]+@(?:(?:[A-Z0-9-]+\\.)?[A-Z]+\\.)?(comenon.com|cherry.se)$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String email) {
        Matcher matcher = EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
}