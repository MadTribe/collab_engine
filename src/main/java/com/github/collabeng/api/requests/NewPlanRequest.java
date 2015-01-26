package com.github.collabeng.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class NewPlanRequest {
    @JsonProperty

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "NewPlanRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
