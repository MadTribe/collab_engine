package com.github.collabeng.api.error;

import static java.lang.String.format;

/**
 * Created by paul.smout on 09/03/2015.
 */
public class PlanNotFoundException extends BusinessException{
    public PlanNotFoundException(Long id) {
        super(format("Plan Not Found for id %d",id));
    }
}
