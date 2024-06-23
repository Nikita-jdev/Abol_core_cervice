package org.core_service.exeption;

public class DataValidationException extends RuntimeException {
    public DataValidationException(String msg) {
        super(msg);
    }
}
