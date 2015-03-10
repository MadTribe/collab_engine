package com.github.collabeng.api.dto;

/**
 * Created by paul.smout on 09/03/2015.
 */
public class PlanSummaryDto {
    private long id;
    private String name;
    private String description;
    private int numSteps;

    public PlanSummaryDto(long id, String name, String description, int numSteps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numSteps = numSteps;
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

    public int getNumSteps() {
        return numSteps;
    }
}
