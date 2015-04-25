package com.github.collabeng.sandboxen;

import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.Task;
import groovy.lang.Closure;
import groovy.lang.Script;
import org.kohsuke.groovy.sandbox.GroovyValueFilter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
* Created by paul.smout on 15/04/2015.
*/
public class TaskEventHandlerSandbox extends BasicSandbox {

    public TaskEventHandlerSandbox() {
        super();
        allowedTypes.add(TaskEventHandlerAPI.class);
        allowedTypes.add(HashMap.class);
        allowedTypes.add(PlanContext.class);
    }



}
