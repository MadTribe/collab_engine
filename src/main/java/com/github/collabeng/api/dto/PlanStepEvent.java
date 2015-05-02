package com.github.collabeng.api.dto;

import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.PlanStepEventParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by paul.smout on 02/05/2015.
 */
public class PlanStepEvent {
    private String name;
    private List<PlanStepEventParameterDto> params = new ArrayList<>();

    public PlanStepEvent() {
    }

    public PlanStepEvent(PlanStepEventEntity eventEntity) {
        this.name = eventEntity.getEventName();
        Set<PlanStepEventParameter> params = eventEntity.getParameters();
        for (PlanStepEventParameter param : params){
            PlanStepEventParameterDto eventParameter = new PlanStepEventParameterDto(param);
            this.params.add(eventParameter);
        }
    }

    public String getName() {
        return name;
    }

    public List<PlanStepEventParameterDto> getParams() {
        return params;
    }
}
