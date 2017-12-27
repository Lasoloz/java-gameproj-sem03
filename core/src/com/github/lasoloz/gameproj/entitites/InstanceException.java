package com.github.lasoloz.gameproj.entitites;

public class InstanceException extends Exception {
    public InstanceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Instance: " + super.getMessage();
    }
}
