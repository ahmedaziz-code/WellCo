package com.springheroes.wellco.exception.entities;

public class UsernameExistException extends Exception {
    public UsernameExistException(String message) {
        super(message);
    }
}
