package com.github.collabeng.eventvalidators;

/**
 * Created by paul.smout on 17/03/2015.
 */
public enum  DefaultValidators {
    NULL(NullValidator.class), PARAMETER(ParameterValidator.class);

    private Class clazz;
    DefaultValidators(Class clazz) {
        this.clazz = clazz;
    }

    public String validatorClassName(){
        return clazz.getName();
    }
}
