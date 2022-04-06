package com.springheroes.wellco.exception.entities;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
