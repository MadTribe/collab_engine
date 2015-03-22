package com.github.collabeng.services;

import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
import com.github.collabeng.dao.PlanDao;
import com.github.collabeng.dao.UserDao;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.UserEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by paul.smout on 13/03/2015.
 */
public class PlanServiceTest extends BaseServiceTest {

    private PlanService instance;



    @Test
    public void testListAll() throws Exception {

    }

    @Test
    public void testGetPlan() throws Exception {

    }

    @Test
    public void testCreatePlan() throws Exception {
        instance =  get(PlanService.class);
//
//
//        final UserDao userDao = get(UserDao.class);
//        UserEntity userEntity = userDao.persist(new UserEntity());
//        setCurrentUser(userEntity);
//
//
//
//
//        List<PlanEntity> plans = get(PlanDao.class).findAll();
//
//        assertThat(plans.size(), equalTo(0));
//
//        instance.createPlan(createPlan("Plan1", "A damned fine plan."));
//
//        assertThat(plans.size(), equalTo(1));
    }

    @Test
    public void testCreatePlanStep() throws Exception {

    }

    @Test
    public void testCreateStepEvent() throws Exception {

    }

    private NewPlanStepRequest createPlanStep(){
        NewPlanStepRequest request = new NewPlanStepRequest();
        return request;
    }

    private NewPlanRequest createPlan(String name, String description){
        NewPlanRequest request = new NewPlanRequest(name, description);
        return request;
    }

}
