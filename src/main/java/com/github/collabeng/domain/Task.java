package com.github.collabeng.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by paul.smout on 22/03/2015.
 */
@Entity
public class Task extends OwnedEntity {

    @ManyToOne
    private TaskContext context;

    private @ManyToOne PlanStepEntity planStep;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private TaskStatus status;

    public Task(){
    }

    public Task(TaskContext context, PlanStepEntity planStep, TaskStatus status) {
        this.context = context;
        this.planStep = planStep;
        this.name = planStep.getName();
        this.description = planStep.getDescription();
        this.status = status;
    }

    public TaskContext getContext() {
        return context;
    }

    public PlanStepEntity getPlanStep() {
        return planStep;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Task withStatus(TaskStatus nextStatus) {
        Task ret = (Task)copy();
        ret.status = nextStatus;
        return ret;
    }

    @Override
    public String toString() {
        return "Task{" +
                " id=" + getId() +
                " name= " + getName() +
                "context=" + context +
                ", planStep=" + planStep +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
