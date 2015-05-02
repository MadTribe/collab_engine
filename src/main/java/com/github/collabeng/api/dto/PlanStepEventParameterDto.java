package com.github.collabeng.api.dto;

import com.github.collabeng.domain.PlanStepEventParameter;

/**
 * Created by paul.smout on 02/05/2015.
 */
public class PlanStepEventParameterDto {
    private String name;
    private String type;

    public PlanStepEventParameterDto() {
    }

    public PlanStepEventParameterDto(PlanStepEventParameter param) {
        this.name = param.getParamName();
        this.type = param.getType();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
