package com.github.lasoloz.gameproj.graphics;

/**
 * Class used for handling graphical exceptions
 */
public class GraphicsException extends Exception {
    private String origin;

    // Package-private constructor
    GraphicsException(String origin, String message) {
        super(message);
        this.origin = origin;
    }

    @Override
    public String getMessage() {
        return "Graphics: " + origin + ": " + super.getMessage();
    }
}
