package com.mercadolibre.iptracer.exceptions;

import org.springframework.http.HttpStatus;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
    }
    public DataAccessException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
