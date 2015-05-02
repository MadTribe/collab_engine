package com.github.collabeng.api.error;

/**
 * Created by paul.smout on 16/04/2015.
 */
public class UnknownScriptException extends BusinessException {
    public UnknownScriptException(String message) {
        super("Unknown Script " + message);
    }

}
