package com.github.collabeng.api.error;

/**
 * Created by paul.smout on 23/04/2015.
 */
public class ErrorMessage {
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
