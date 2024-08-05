package com.dirsynch.directorysynchronizer.core.validator;


import com.dirsynch.directorysynchronizer.core.exception.ValidException;
import com.dirsynch.directorysynchronizer.di.annotation.Component;

@Component
public class Validator {

    public void isNumber(String value) throws ValidException {
        boolean matches = value.matches("^\\d+$");
        if (!matches)
            throw new ValidException(String.format("String %s is not number!", value));
    }

    public void minMaxPasswordLength(String value) throws ValidException {
        if (value.length() < 3)
            throw new ValidException("Password is too short");
        if (value.length() > 20)
            throw new ValidException("Password is too long");
    }

    public void isNull(String value) throws ValidException {
        if (value == null)
            throw new ValidException("Field can't be null");
    }

    public void isEmpty(String value) throws ValidException {
        if (value.isEmpty())
            throw new ValidException(String.format("Value %s can't be empty", value));
    }
}
