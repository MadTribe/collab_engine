package com.github.collabeng.api.dto;

import java.util.List;

/**
 * Created by paul.smout on 09/03/2015.
 */
public class PlanStepDto {
    private long id;
    private String name;
    private String description;

    public PlanStepDto(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
