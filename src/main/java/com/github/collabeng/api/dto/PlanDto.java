package com.github.collabeng.api.dto;

import java.util.List;

/**
 * Created by paul.smout on 09/03/2015.
 */
public class PlanDto {
    private long id;
    private String name;
    private String description;
    private List<PlanStepDto> steps;

    public PlanDto(long id, String name, String description, List<PlanStepDto> steps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.steps = steps;
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

    public List<PlanStepDto> getSteps() {
        return steps;
    }
}
