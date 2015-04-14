package com.github.collabeng.api.requests;

/**
 * Created by paul.smout on 13/03/2015.
 */
public class NewStepEventRequest {
    private String name;
    private Long planStepId;
    private Long nextStepId;
    private String eventValidator;

    public NewStepEventRequest() {
    }

    public NewStepEventRequest(String name, Long planStepId, Long nextStepId, String eventValidator) {
        this.name = name;
        this.planStepId = planStepId;
        this.nextStepId = nextStepId;
        this.eventValidator = eventValidator;
    }

    public String getEventValidator() {
        return eventValidator;
    }

    public String getName() {
        return name;
    }

    public Long getPlanStepId() {
        return planStepId;
    }

    public Long getNextStepId() {
        return nextStepId;
    }
}
