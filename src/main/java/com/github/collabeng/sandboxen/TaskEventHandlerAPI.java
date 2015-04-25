package com.github.collabeng.sandboxen;

import com.github.collabeng.api.error.UnknownParameterException;
import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.PlanStepEventParameter;
import com.github.collabeng.domain.Task;
import org.hibernate.loader.plan.build.spi.LoadPlanBuildingContext;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by paul.smout on 23/04/2015.
 */
public class TaskEventHandlerAPI {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventMessage eventMessage;
    private final Task task;
    private final PlanStepEventEntity eventDefinition;


    public TaskEventHandlerAPI(PlanStepEventEntity eventDefinintion, Task task, EventMessage eventMessage) {
        this.task = task;
        this.eventDefinition = eventDefinintion;
        this.eventMessage = eventMessage;
    }

    public Map<String,Object> getParamsAsObject(String... paramNames){
        Map<String, String> params = eventMessage.getParams();
        Map<String, Object> ret = new HashMap<>();

        for (String paramName : paramNames){
           if (params.containsKey(paramName)){
               String value = params.get(paramName);
               ret.put(paramName, value);
           } else {
               throw new UnknownParameterException(paramName);
           }

        }
        LOG.info("Returning. " + ret);

        return ret;
    }

    public PlanContext context(){
        return new PlanContext();
    }


}
