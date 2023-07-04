package com.upc.taller_eb_sec.exception;

public class ValidationException extends RuntimeException{
    public ValidationException() {}

    public ValidationException(String message) {super(message);}
}
