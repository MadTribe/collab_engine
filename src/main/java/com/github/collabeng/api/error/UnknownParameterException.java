package com.github.collabeng.api.error;

/**
 * Created by paul.smout on 25/04/2015.
 */
public class UnknownParameterException extends ScriptException{
    public UnknownParameterException(String paramName) {
        super("Unable to locate event parameter " + paramName, null);

    }
}
