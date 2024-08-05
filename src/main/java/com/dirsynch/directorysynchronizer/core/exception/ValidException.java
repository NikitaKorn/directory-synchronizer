package com.dirsynch.directorysynchronizer.core.exception;

import java.io.Serial;

public class ValidException extends Exception {
    @Serial
    private static final long serialVersionUID = -3119160938036219811L;
    private static final String PREFIX = "GUI validation exception. ";

    public ValidException(String message) {
        super(PREFIX + message);
    }
}
