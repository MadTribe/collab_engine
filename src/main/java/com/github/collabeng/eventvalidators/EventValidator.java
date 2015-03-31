package com.github.collabeng.eventvalidators;

import com.github.collabeng.domain.PlanStepEventEntity;

/**
 * Created by paul.smout on 29/03/2015.
 */
public interface EventValidator {
    boolean isValid(PlanStepEventEntity eventEntity);
}
