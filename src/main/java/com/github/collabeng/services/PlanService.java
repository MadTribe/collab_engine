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
    @Named(CURRENT_USER_ENTITY)
    private Provider<UserEntity> currentUser;

    @Inject
    private PlanDao planDaoProvider;

    @Transactional
    public List<PlanEntity> listAll(){
        LOG.info(" current user  " + currentUser.get() );

        return this.planDaoProvider.findAll();
    }

    @Transactional
    public void createPlan(NewPlanRequest newPlan) {
        PlanEntity plan = new PlanEntity(newPlan.getName(),
                                         newPlan.getDescription());
        planDaoProvider.persist(plan);
        planDaoProvider.flush();
    }
}