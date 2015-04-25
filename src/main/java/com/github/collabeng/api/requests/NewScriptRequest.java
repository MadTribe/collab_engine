package com.github.collabeng.api.requests;

import com.github.collabeng.domain.ScriptType;

import java.util.Map;

/**
 * Created by paul.smout on 16/04/2015.
 */
public class NewScriptRequest {
    private String name;
    private String source;
    private String type = ScriptType.EVENT_HANDLER.name();
    private Map<String, String> params;

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "NewScriptRequest{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", params=" + params +
                '}';
    }
}
