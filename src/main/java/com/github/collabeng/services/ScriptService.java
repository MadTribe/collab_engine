package com.github.collabeng.services;

import com.github.collabeng.sandboxen.GroovySandbox;

import javax.inject.Inject;

/**
 * Created by paul.smout on 14/04/2015.
 */
public class ScriptService {
    @Inject
    private GroovySandbox sandbox;

    public String runScript(String script){
        return sandbox.scriptRunner(script,"");
    }
}
