package com.github.collabeng.services;

import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.dao.PlanDao;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.UserEntity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;
import com.google.inject.servlet.RequestScoped;

import java.util.List;
import java.util.logging.Logger;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;

@RequestScoped
public class PlanService {
    private static final Logger LOG = Logger.getLogger(PlanService.class.getName());


    @Inject
    private PlanDao planDaoProvider;

    @Transactional
    public List<PlanEntity> listAll(){

        return this.planDaoProvider.findAll();
    }

    @Transactional
    public void createPlan(NewPlanRequest newPlan) {
        PlanEntity plan = new PlanEntity(null,
                                         newPlan.getName(),
                                         newPlan.getDescription());
        planDaoProvider.persist(plan);
    }
}