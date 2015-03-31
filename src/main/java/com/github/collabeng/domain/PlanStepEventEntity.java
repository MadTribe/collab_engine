package com.github.collabeng.domain;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 13/03/2015.
 */
@Entity
public class PlanStepEventEntity extends OwnedEntity {

    @NotNull
    private String eventName;

    @NotNull
    private String eventValidator;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    private PlanStepEntity owningStep;


    @ManyToOne
    @JoinColumn(name = "NEXT_STEP_ID")
    private PlanStepEntity nextStep;


    private TaskStatus nextStatus;

    public PlanStepEventEntity() {
    }

    public PlanStepEventEntity(String eventName, String eventValidator, PlanStepEntity owningStep, PlanStepEntity nextStep, TaskStatus nextStatus) {
        this.eventName = eventName;
        this.eventValidator = eventValidator;
        this.owningStep = owningStep;
        this.nextStep = nextStep;
        this.nextStatus = nextStatus;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventValidator() {
        return eventValidator;
    }

    public PlanStepEntity getOwningStep() {
        return owningStep;
    }

    public PlanStepEntity getNextStep() {
        return nextStep;
    }

    @Override
    public String toString() {
        return "PlanStepEventEntity{" +
                "eventName='" + eventName + '\'' +
                ", eventValidator='" + eventValidator + '\'' +
                ", owningStep=" + owningStep +
                ", nextStep=" + nextStep +
                '}';
    }

    public TaskStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(TaskStatus nextStatus) {
        this.nextStatus = nextStatus;
    }
}
