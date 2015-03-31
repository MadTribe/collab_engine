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
public class PlanEntity extends OwnedEntity {
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;


    @OneToMany(mappedBy = "owningPlan")
    @OrderBy("createdAt DESC")
    @BatchSize(size = 10)
    private List<PlanStepEntity> steps = new ArrayList<>();

    @OneToOne
    private PlanStepEntity firstStep;


    private PlanEntity() {
    }

    public PlanEntity( String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public List<PlanStepEntity> getSteps() {
        return steps;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "" + super.toString() +
                "name='" + name + '\'' +
                ", description='" + description;
    }

    public PlanEntity withName(String name) {
        PlanEntity ret = (PlanEntity)copy();
        ret.name = name;
        return ret;
    }

    public PlanEntity withFirstStep(PlanStepEntity firstStep) {
        PlanEntity ret = (PlanEntity)copy();
        ret.firstStep = firstStep;
        return ret;
    }

    public String getName() {
        return name;
    }

    public PlanStepEntity getFirstStep() {
        return firstStep;
    }

}
