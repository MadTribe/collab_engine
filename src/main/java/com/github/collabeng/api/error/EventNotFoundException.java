package com.github.collabeng.api.error;

import static java.lang.String.format;

/**
 * Created by paul.smout on 29/03/2015.
 */
public class EventNotFoundException extends BusinessException{
    public EventNotFoundException(String name) {
        super(format("Event not found for task with name %s",name));
    }
}
