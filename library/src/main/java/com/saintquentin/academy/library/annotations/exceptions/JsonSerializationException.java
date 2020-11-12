package com.saintquentin.academy.customannotations.exceptions;

public class JsonSerializationException extends RuntimeException {
    public JsonSerializationException(String message) {
        super(message);
    }
}
