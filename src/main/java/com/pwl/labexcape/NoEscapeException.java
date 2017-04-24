package com.pwl.labexcape;

public class NoEscapeException extends Exception {

    public NoEscapeException(String message) {
        super(message);
    }

    public NoEscapeException(String message, Object... param) {
        super(String.format(message, param));
    }


}
