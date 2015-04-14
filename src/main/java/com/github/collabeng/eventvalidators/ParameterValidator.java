package com.github.collabeng.eventvalidators;

import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.PlanStepEventParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

/**
 * Created by paul.smout on 17/03/2015.
 */
public class ParameterValidator implements EventValidator {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Override
    public boolean isValid(PlanStepEventEntity eventEntity, EventMessage message) {
        LOG.info("validating message {}", message);
        boolean ret = true;
        Set<PlanStepEventParameter> params = eventEntity.getParameters();
        LOG.info("should conform to {}", params.size());
        for (PlanStepEventParameter parameter : params){
            LOG.info("looking for parameter {}", parameter.getParamName());
             if (message.getParams().containsKey(parameter.getParamName())){
                 String rawValue = message.getParams().get(parameter.getParamName());
                 String type = parameter.getType();
                 ret &= validateParameter(rawValue, type);

             } else {
                 LOG.error("Parameter {} is missing", parameter.getParamName());
                 ret = false;
             }
        }

        return ret;
    }

    private boolean validateParameter(String rawValue, String type){
        return true;
    }
}
