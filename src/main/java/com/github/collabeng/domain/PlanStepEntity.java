package com.github.collabeng.domain;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "owningStep")
    private List<PlanStepEventEntity> knownPossibleEvents = new ArrayList<>();;


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

    public PlanEntity getOwningPlan() {
        return owningPlan;
    }

    public List<PlanStepEventEntity> getKnownPossibleEvents() {
        return knownPossibleEvents;
    }
}
