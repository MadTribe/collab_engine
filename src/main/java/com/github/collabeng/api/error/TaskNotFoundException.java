package com.github.collabeng.api.error;

import static java.lang.String.format;

/**
 * Created by paul.smout on 29/03/2015.
 */
public class TaskNotFoundException extends BusinessException {
    public TaskNotFoundException(Long taskId) {
        super(format("User has no Task with id %d."));
    }
}
