package com.github.collabeng.dao;

import com.github.collabeng.domain.PlanEntity;

import static java.util.Arrays.asList;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class PlanDao extends NamedOwnedDao<PlanEntity> {

    public void clearMyFirstSteps(){

        nativeUpdate("firstStep_ID=NULL",
                "",
                asList());
    }



}
