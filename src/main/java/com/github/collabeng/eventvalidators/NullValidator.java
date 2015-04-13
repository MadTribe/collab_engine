package com.github.collabeng.eventvalidators;

import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.domain.PlanStepEventEntity;

/**
 * Created by paul.smout on 17/03/2015.
 */
public class NullValidator implements EventValidator {

    @Override
    public boolean isValid(PlanStepEventEntity eventEntity, EventMessage message) {
        return true;
    }
}
