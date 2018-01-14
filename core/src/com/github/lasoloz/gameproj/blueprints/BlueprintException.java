package com.github.lasoloz.gameproj.blueprints;

/**
 * Exception class for blueprints, deriving from Exception
 */
public class BlueprintException extends Exception {
    private String tag;

    BlueprintException(String tag, String message) {
        super(message);
        this.tag = tag;
    }

    @Override
    public String getMessage() {
        return "Blueprint: " + tag + ": " + super.getMessage();
    }
}
