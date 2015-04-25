package com.github.collabeng.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 22/03/2015.
 */
@Entity
public class ScriptParameter extends OwnedEntity{

    @NotNull
    @ManyToOne
    @JoinColumn(name = "SCRIPT_ID")
    private Script owningScript;
;
    private String paramName;
    private String type;

    public ScriptParameter() {
    }

    public ScriptParameter(Script owningScript, String paramName, String type) {
        this.owningScript = owningScript;
        this.paramName = paramName;
        this.type = type;
    }

    public Script getOwningScript() {
        return owningScript;
    }

    public String getParamName() {
        return paramName;
    }

    public String getType() {
        return type;
    }
}
