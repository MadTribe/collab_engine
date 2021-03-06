package com.github.collabeng.api.requests;

import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class NewPlanStepRequest {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Long planId;

    public NewPlanStepRequest() {
    }

    public NewPlanStepRequest(String name, String description, Long planId) {
        this.name = name;
        this.description = description;
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getPlanId() {
        return planId;
    }
}
