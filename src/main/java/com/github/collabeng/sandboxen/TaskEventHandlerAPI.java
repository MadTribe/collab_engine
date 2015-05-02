package com.github.collabeng.sandboxen;

import com.github.collabeng.api.error.UnknownParameterException;
import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.PlanStepEventParameter;
import com.github.collabeng.domain.Task;
import org.hibernate.loader.plan.build.spi.LoadPlanBuildingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by paul.smout on 23/04/2015.
 */
public class TaskEventHandlerAPI {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventMessage eventMessage;
    private final Task task;
    private final PlanStepEventEntity eventDefinition;
    private final PlanContext context;

    public TaskEventHandlerAPI(PlanStepEventEntity eventDefinition,
                               Task task,
                               EventMessage eventMessage,
                               PlanContext context) {
        this.task = task;
        this.eventDefinition = eventDefinition;
        this.eventMessage = eventMessage;
        this.context = context;
    }

    public Object getParamValue(String name){
        Object ret = null;
        Map<String, String> params = eventMessage.getParams();

        boolean found = false;
        for (String paramName : params.keySet()){
            if (paramName.equals(name)){
                ret = params.get(paramName);
                found = true;
            }
        }

        if( !found) {
            throw new UnknownParameterException(name);
        }


        return ret;
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

    public String sendEmail(String to, String subject, String content){
        LOG.info("Sending Email to {} with subject {} and content {}.", to, subject, content);
        return "XXX";

    }

    /**
     * Using extremely simple template approach until the security implications of
     * User submitting templates is better understood.
     * @param template
     * @param binding
     * @return
     */
    public String expandTemplate(String template, Map<String,Object> binding){
        LOG.info("Expanding Template {} with {}", template, binding);
        String ret = template;
        Set<String> keys = binding.keySet();

        for (String key: keys){
            ret = ret.replace("${"+ key +"}", binding.get(key).toString());
        }

        return ret;

    }

    public PlanContext context(){
        return context;
    }


}
