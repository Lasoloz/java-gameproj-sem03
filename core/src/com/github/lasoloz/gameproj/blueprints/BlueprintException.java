package com.github.lasoloz.gameproj.blueprints;

public class BlueprintException extends Exception {
    public BlueprintException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Blueprint: " + super.getMessage();
    }
}
