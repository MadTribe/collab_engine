package com.github.collabeng.domain;

import com.github.collabeng.dao.NamedOwnedDao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paul.smout on 13/03/2015.
 */
@Entity
public class Script extends NamedOwnedEntity {

    @Lob
//    @Column(name = "scriptContent", nullable = false, length = 1000)
    private String scriptContent;

    @NotNull
    private ScriptType type;

    @OneToMany(mappedBy = "owningScript")
    private Set<ScriptParameter> parameters = new HashSet<>();

    public Script() {
    }

    public Script(String name, String scriptContent, ScriptType type, Set<ScriptParameter> parameters) {
        super(name);
        this.scriptContent = scriptContent;
        this.type = type;
        this.parameters = parameters;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public ScriptType getType() {
        return type;
    }

    public Set<ScriptParameter> getParameters() {
        return parameters;
    }

    public Script withScriptContent(String content){
        Script ret = (Script)copy();
        ret.scriptContent = content;
        return ret;
    }

}
