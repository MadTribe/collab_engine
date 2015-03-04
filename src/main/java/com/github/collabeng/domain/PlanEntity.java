package com.github.collabeng.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * Created by paul.smout on 26/01/2015.
 */
@Entity
public class PlanEntity extends OwnedEntity {
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    private PlanEntity() {
    }

    public PlanEntity( UserEntity owner, String name, String description) {
        super(owner);
        this.name = name;
        this.description = description;
    }

    public PlanEntity(PlanEntity planEntity) {
        super(planEntity);
        this.name = planEntity.name;
        this.description = planEntity.description;
    }

    public String getName() {
        return name;
    }
    public PlanEntity withName(String name){
        PlanEntity ret = new PlanEntity(this);
        ret.name = name;
        return ret;
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
}
