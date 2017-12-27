package com.github.lasoloz.gameproj.graphics;

public class GraphicsException extends Exception {
    String origin;

    public GraphicsException(String origin, String message) {
        super(message);
        this.origin = origin;
    }

    @Override
    public String getMessage() {
        return "Graphics: " + origin + ": " + getMessage();
    }
}
