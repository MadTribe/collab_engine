package com.github.collabeng.domain;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by paul.smout on 13/03/2015.
 */
@Entity
public class PlanStepEventEntity extends OwnedEntity {

    @NotNull
    private String eventName;

    @NotNull
    private String eventValidator;

    @ManyToOne
    @JoinColumn(name = "SCRIPT_ID")
    private Script eventHandler;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "STEP_ID")
    private PlanStepEntity owningStep;


    @ManyToOne
    @JoinColumn(name = "NEXT_STEP_ID")
    private PlanStepEntity nextStep;

    @OneToMany(mappedBy = "owningEvent")
    private Set<PlanStepEventParameter> parameters = new HashSet<>();


    private TaskStatus nextStatus;

    public PlanStepEventEntity() {
    }



    public Script getEventHandler() {
        return eventHandler;
    }

    public PlanStepEventEntity(String eventName, String eventValidator, Script eventHandler, PlanStepEntity owningStep, PlanStepEntity nextStep, TaskStatus nextStatus) {
        this.eventName = eventName;
        this.eventValidator = eventValidator;
        this.eventHandler = eventHandler;

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

    public Set<PlanStepEventParameter> getParameters() {
        return parameters;
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

}
