package com.github.collabeng.sandboxen;

import com.github.collabeng.api.error.BusinessException;

/**
 * Created by paul.smout on 16/04/2015.
 */
public class ScriptSecurityException extends BusinessException {
    public ScriptSecurityException(String message) {
        super(message);
    }
}
