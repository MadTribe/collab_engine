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

    public PlanEntity( String name, String description) {
        super();
        this.name = name;
        this.description = description;
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

    public String getName() {
        return name;
    }
}
