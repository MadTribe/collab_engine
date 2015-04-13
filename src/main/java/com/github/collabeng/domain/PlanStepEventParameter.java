package com.github.collabeng.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul.smout on 22/03/2015.
 */
@Entity
public class PlanStepEventParameter extends OwnedEntity{

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EVENT_ID")
    private PlanStepEventEntity owningEvent;
;
    private String paramName;
    private String type;

    public PlanStepEventParameter(PlanStepEventEntity owningEvent, String paramName, String type) {
        this.owningEvent = owningEvent;
        this.paramName = paramName;
        this.type = type;
    }

    public PlanStepEventEntity getOwningEvent() {
        return owningEvent;
    }

    public String getParamName() {
        return paramName;
    }

    public String getType() {
        return type;
    }
}
