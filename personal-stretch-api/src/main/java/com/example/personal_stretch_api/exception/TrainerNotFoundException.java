package com.example.personal_stretch_api.exception;

public class TrainerNotFoundException extends RuntimeException{
    public TrainerNotFoundException(String message) {
        super(message);
    }
}