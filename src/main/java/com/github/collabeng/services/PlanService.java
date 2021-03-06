package com.github.collabeng.services;

import com.github.collabeng.api.dto.PlanDto;
import com.github.collabeng.api.dto.PlanStepDto;
import com.github.collabeng.api.dto.PlanSummaryDto;
import com.github.collabeng.api.error.ItemNotFoundException;
import com.github.collabeng.api.error.UnknownPlanException;
import com.github.collabeng.api.error.UnknownPlanStepException;
import com.github.collabeng.api.error.UnknownScriptException;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
import com.github.collabeng.api.requests.NewStepEventRequest;
import com.github.collabeng.api.responses.NewEntityResponse;
import com.github.collabeng.dao.*;
import com.github.collabeng.domain.*;
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
    private TaskDao taskDao;

    @Inject
    private PlanDao planDaoProvider;

    @Inject
    private PlanStepDao planStepDao;

    @Inject
    private ScriptDao scriptDao;

    @Inject
    private PlanStepEventDao planStepEventDao;

    @Inject
    private ContextService contextService;

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
    public NewEntityResponse createPlan(NewPlanRequest newPlan) {
        LOG.info("Creating plan ");
        PlanEntity plan = new PlanEntity(newPlan.getName(),
                                         newPlan.getDescription());
        plan = planDaoProvider.persist(plan);

        return new NewEntityResponse(plan.getId());
    }

    @Transactional
    public NewEntityResponse createPlanStep(NewPlanStepRequest newPlanStep,
                                              long planId) throws UnknownPlanException {
        LOG.info("Creating plan step for plan {}", planId);
        PlanEntity plan = planDaoProvider.find(planId).orElseThrow(() -> new UnknownPlanException(format("Plan with id %s either doesn't exist on not owned by this user","" + planId )));

        PlanStepEntity planStep = new PlanStepEntity(plan,newPlanStep.getName(),newPlanStep.getDescription());
        planStep = planStepDao.persist(planStep);

        if ( plan.getSteps().size() == 1){
            LOG.info("Adding first step {} to plan {}", newPlanStep, planId);
            planDaoProvider.merge(plan.withFirstStep(planStep));
        }

        return new NewEntityResponse(planStep.getId());
    }


    @Transactional
    public NewEntityResponse createStepEvent(NewStepEventRequest stepEventRequest){
        PlanStepEntity owner = planStepDao.find(stepEventRequest.getPlanStepId()).orElseThrow(() -> new UnknownPlanStepException(stepEventRequest.getPlanStepId()));

        PlanStepEntity next = null;
        LOG.info("Nest Step Id is {}",stepEventRequest.getNextStepId());
        if (stepEventRequest.getNextStepId() != null){
            next = planStepDao.find(stepEventRequest.getNextStepId()).orElseThrow(() -> new UnknownPlanStepException(stepEventRequest.getNextStepId()));
        }
        String name = stepEventRequest.getName();

        Script eventHandler = null;
        if (stepEventRequest.getHandlerName() != null && !stepEventRequest.getHandlerName().isEmpty()){
            eventHandler = scriptDao.findByName(stepEventRequest.getHandlerName()).orElseThrow(() -> new UnknownScriptException(stepEventRequest.getHandlerName()));
        }

        PlanStepEventEntity event = new PlanStepEventEntity(name, getEventValidator(stepEventRequest.getEventValidator()).validatorClassName(), eventHandler, owner, next, TaskStatus.FINISHED);

        event = planStepEventDao.persist(event);
        return new NewEntityResponse(event.getId());

    }

    private DefaultValidators getEventValidator(String name){
        DefaultValidators ret = DefaultValidators.NULL;

        try {
            if (name != null){
                ret = DefaultValidators.valueOf(name);
            }
        } catch (IllegalArgumentException iae){
            LOG.error("Invalid validator type {} ", name);
        }
        return ret;
    }

    @Transactional
    public void beginPlan(Long id) {
        PlanEntity planEntity =  planDaoProvider.find(id).orElseThrow(() -> new ItemNotFoundException("Plan", id));
        PlanStepEntity step = planEntity.getFirstStep();

        TaskContext context = contextService.createNewContext();

        Task task = new Task(context, step, TaskStatus.IN_PROGRESS);

        taskDao.persist(task);

    }
}