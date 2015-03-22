package com.github.collabeng.api.error;

/**
 * Created by paul.smout on 17/03/2015.
 */
public class UnknownPlanStepException extends BusinessException{
    public UnknownPlanStepException(Long planStepId) {
        super("Unknown or inaccessible Plan Step with id " + planStepId );
    }
}
