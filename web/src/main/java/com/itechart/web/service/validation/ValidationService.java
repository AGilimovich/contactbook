package com.itechart.web.service.validation;

import java.util.regex.Pattern;

/**
 * Created by Aleksandr on 22.03.2017.
 */
public class ValidationService implements AbstractValidationService {
    private final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final Pattern emailPattern = Pattern.compile(emailRegex);

    public boolean validateEmail(String email) {
        if (email == null) return false;
        if (emailPattern.matcher(email).matches())
            return true;
        else return false;
    }
}
