package com.github.collabeng.services;

import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.dao.PlanDao;
import com.github.collabeng.domain.PlanEntity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import java.util.List;

public class PlanService {
    @Inject
    private Provider<PlanDao> planDaoProvider;

    @Transactional
    public List<PlanEntity> listAll(){
        return this.planDaoProvider.get().findAll();
    }

    @Transactional
    public void createPlan(NewPlanRequest newPlan) {
        PlanEntity plan = new PlanEntity(newPlan.getName(),
                                         newPlan.getDescription());
        planDaoProvider.get().persist(plan);
        planDaoProvider.get().flush();
    }
}