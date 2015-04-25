package com.github.collabeng.api;

import com.github.collabeng.api.dto.PlanDto;
import com.github.collabeng.api.dto.PlanSummaryDto;
import com.github.collabeng.api.error.ItemNotFoundException;
import com.github.collabeng.api.error.UnknownPlanException;
import com.github.collabeng.api.requests.BeginPlanRequest;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
import com.github.collabeng.api.requests.NewStepEventRequest;
import com.github.collabeng.api.responses.NewEntityResponse;
import com.github.collabeng.services.PlanService;
import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Path("/api/plan")
public class PlanResource {

    private static final Logger LOG = Logger.getLogger(PlanResource.class.getName());

    @Inject
    private PlanService planService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanSummaryDto> list(@Context HttpServletRequest request) {
        final List<PlanSummaryDto> collect = planService.listAll();
        return collect;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{planId}")
    public PlanDto readPlan(@PathParam("planId") long planId) {
        final Optional<PlanDto> collect = planService.getPlan(planId);
        return collect.orElseThrow(() -> new ItemNotFoundException("Plan", planId));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public NewEntityResponse createPlan(NewPlanRequest newPlan) {
        LOG.info("Create New Plan: " + newPlan);
        return planService.createPlan(newPlan);
    }

     @POST
     @Produces(MediaType.APPLICATION_JSON)
     @Path("{planId}/step")
     public NewEntityResponse createStep(@PathParam("planId") long planId,
                                           NewPlanStepRequest newPlanStep) throws UnknownPlanException {
        LOG.info("Creating new plan step " + newPlanStep);
        return planService.createPlanStep(newPlanStep, planId);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("event")
    public NewEntityResponse createStepEvent(NewStepEventRequest stepEventRequest) throws UnknownPlanException {
        LOG.info("Creating new plan step event " + stepEventRequest);
        return planService.createStepEvent(stepEventRequest);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("begin")
    public Response beginPlan(BeginPlanRequest request) throws UnknownPlanException {
        LOG.info("Beginning plan step event " + request.getId());
        planService.beginPlan(request.getId());
        return Response.ok().build();
    }
}
