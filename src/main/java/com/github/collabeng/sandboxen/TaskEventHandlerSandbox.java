package com.github.collabeng.sandboxen;

import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.Task;
import groovy.lang.Closure;
import groovy.lang.Script;
import org.bson.Document;
import org.kohsuke.groovy.sandbox.GroovyValueFilter;

import java.util.*;

/**
* Created by paul.smout on 15/04/2015.
*/
public class TaskEventHandlerSandbox extends BasicSandbox {

    public TaskEventHandlerSandbox() {
        super();
        // These should come from the script's Imports/permissions
        allowedTypes.add(TaskEventHandlerAPI.class);
        allowedTypes.add(HashMap.class);
        allowedTypes.add(PlanContext.class);
        allowedTypes.add(ArrayList.class);
        allowedTypes.add(Document.class);
    }



}
