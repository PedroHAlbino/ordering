package com.albinos.ordering.domain.model.validator;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.ObjectStreamClass;
import java.util.Objects;

public class FielValidations {

    private FielValidations(){}

    public static void requiresNonBlank(String value){
        requiresNonBlank(value, "");
    }

    public static void requiresNonBlank(String value, String errorMessage){
        Objects.requireNonNull(value);
        if(value.isBlank()){
            throw new IllegalArgumentException();
        }
    }

    public static void requiresValidEmail(String email){
        requiresValidEmail(email, null);
    }

    public static void requiresValidEmail(String email, String errorMessage){
        Objects.requireNonNull(email, errorMessage);
        if(email.isBlank()){
            throw new IllegalArgumentException(errorMessage);
        }
        if(!EmailValidator.getInstance().isValid(email)){
            throw new IllegalArgumentException(errorMessage);
        }

    }
}
