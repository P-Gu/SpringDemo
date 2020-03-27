package com.example.demo.exception;

import java.lang.Exception;

public class PasswordException extends Exception {

    public PasswordException(String errorMessage) {
        super(errorMessage);
    }
}
