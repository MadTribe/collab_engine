package com.github.collabeng.services;

import com.github.collabeng.api.dto.PlanDto;
import com.github.collabeng.api.dto.PlanStepDto;
import com.github.collabeng.api.dto.PlanSummaryDto;
import com.github.collabeng.api.error.UnknownPlanException;
import com.github.collabeng.api.error.UnknownPlanStepException;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
import com.github.collabeng.api.requests.NewStepEventRequest;
import com.github.collabeng.api.responses.NewPlanStepResponse;
import com.github.collabeng.dao.PlanDao;
import com.github.collabeng.dao.PlanStepDao;
import com.github.collabeng.dao.PlanStepEventDao;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.PlanStepEntity;
import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.eventvalidators.DefaultValidators;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.google.inject.servlet.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RequestScoped
public class PlanService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private PlanDao planDaoProvider;

    @Inject
    private PlanStepDao planStepDao;

    @Inject
    private PlanStepEventDao planStepEventDao;

    public PlanService() {
    }

    public List<PlanSummaryDto> listAll(){
        List<PlanSummaryDto> planSummaryDtos = new ArrayList<>();
        List<PlanEntity> plans = this.planDaoProvider.findAll();

        plans.forEach(plan -> planSummaryDtos.add(new PlanSummaryDto(plan.getId(), plan.getName(), plan.getDescription(), plan.getSteps().size())));
        return planSummaryDtos;
    }

    public Optional<PlanDto> getPlan(long plainId){
        Optional<PlanDto> ret = Optional.empty();

        Optional<PlanEntity> planEntityOptional = this.planDaoProvider.find(plainId);

        if (planEntityOptional.isPresent()){
            PlanEntity plan = planEntityOptional.get();
            List<PlanStepDto> steps = new ArrayList<>();
            plan.getSteps().forEach(step -> steps.add(new PlanStepDto(step.getId(),step.getName(),step.getDescription())));
            ret = Optional.of(new PlanDto(plan.getId(), plan.getName(),plan.getDescription(),steps));
        }
        return ret;
    }


    @Transactional
    public void createPlan(NewPlanRequest newPlan) {
        PlanEntity plan = new PlanEntity(newPlan.getName(),
                                         newPlan.getDescription());
        planDaoProvider.persist(plan);
    }

    @Transactional
    public NewPlanStepResponse createPlanStep(NewPlanStepRequest newPlanStep,
                                              long planId) throws UnknownPlanException {
        PlanEntity plan = planDaoProvider.find(planId).orElseThrow(() -> new UnknownPlanException(format("Plan with id %s either doesn't exist on not owned by this user","" + planId )));

        PlanStepEntity planStep = new PlanStepEntity(plan,newPlanStep.getName(),newPlanStep.getDescription());

        planStep = planStepDao.persist(planStep);

        return new NewPlanStepResponse(planStep.getId(),planStep.getCreatedAt());
    }


    @Transactional
    public void createStepEvent(NewStepEventRequest stepEventRequest){
        PlanStepEntity owner = planStepDao.find(stepEventRequest.getPlanStepId()).orElseThrow(() -> new UnknownPlanStepException(stepEventRequest.getPlanStepId()));

        PlanStepEntity next = planStepDao.find(stepEventRequest.getPlanStepId()).orElseThrow(() -> new UnknownPlanStepException(stepEventRequest.getNextStepId()));

        String name = stepEventRequest.getName();

        PlanStepEventEntity event = new PlanStepEventEntity(name, DefaultValidators.NULL.validatorClassName(), owner, next);

        planStepEventDao.persist(event);
    }




}