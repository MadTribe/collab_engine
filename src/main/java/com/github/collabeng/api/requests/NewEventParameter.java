package com.github.collabeng.api.requests;

/**
 * Created by paul.smout on 06/04/2015.
 */
public class NewEventParameter {
    private Long owningEventId;
    private String paramName;
    private String type;

    public NewEventParameter() {
    }

    public Long getOwningEventId() {
        return owningEventId;
    }

    public String getParamName() {
        return paramName;
    }

    public String getType() {
        return type;
    }
}
