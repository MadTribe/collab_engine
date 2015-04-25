package com.github.collabeng.sandboxen;

import org.hibernate.loader.plan.build.spi.LoadPlanBuildingContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul.smout on 23/04/2015.
 */
public class TaskEventHandlerAPI {

    public Map<String,Object> getParamsAsObject(String... paramNames){
        return new HashMap();
    }

    public PlanContext context(){
        return new PlanContext();
    }


}
