package com.github.collabeng.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 26/01/2015.
 */
@Entity
public class PlanStepEntity extends OwnedEntity {
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PLAN_ID")
    private PlanEntity owningPlan;

    public PlanStepEntity() {
    }

    public PlanStepEntity(PlanEntity owningPlan, String name, String description) {
        this.name = name;
        this.description = description;
        this.owningPlan = owningPlan;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
