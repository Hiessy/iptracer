package com.mercadolibre.iptracer.exceptions;

/**
 * Exception class that represents errors on service layer.
 */
public class ServiceException extends Exception {

    public ServiceException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}