package com.github.collabeng.api.dto;

import com.github.collabeng.domain.Script;
import com.github.collabeng.domain.ScriptType;

import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 20/04/2015.
 */
public class ScriptDto extends NamedEntityDto {
    @NotNull
    private String scriptContent;

    @NotNull
    private String type;

    public ScriptDto() {
    }

    public ScriptDto(Script script){
        super(script);
        this.scriptContent = script.getScriptContent();
        this.type = script.getType().name();
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public String getType() {
        return type;
    }
}
