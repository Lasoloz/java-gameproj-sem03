package com.github.lasoloz.gameproj.entitites;

public class BlueprintException extends Exception {
    private String tag;

    public BlueprintException(String tag, String message) {
        super(message);
        this.tag = tag;
    }

    @Override
    public String getMessage() {
        return "Blueprint: " + tag + ": " + super.getMessage();
    }
}
