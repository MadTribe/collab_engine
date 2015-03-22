package com.github.collabeng.api;

import com.github.collabeng.api.dto.PlanDto;
import com.github.collabeng.api.dto.PlanSummaryDto;
import com.github.collabeng.api.error.PlanNotFoundException;
import com.github.collabeng.api.error.UnknownPlanException;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
import com.github.collabeng.api.requests.NewStepEventRequest;
import com.github.collabeng.api.responses.NewPlanStepResponse;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.services.PlanService;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

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
    public PlanDto get(@PathParam("planId") long planId) {
        final Optional<PlanDto> collect = planService.getPlan(planId);
        return collect.orElseThrow(() -> new PlanNotFoundException((int) planId));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(NewPlanRequest newPlan) {
        LOG.info("" + newPlan);
        planService.createPlan(newPlan);
        return Response.ok().build();
    }

     @POST
     @Produces(MediaType.APPLICATION_JSON)
     @Path("{planId}/step")
     public NewPlanStepResponse createStep(@PathParam("planId") long planId,
                                           NewPlanStepRequest newPlanStep) throws UnknownPlanException {
        LOG.info("Creating new plan step " + newPlanStep);
        return planService.createPlanStep(newPlanStep, planId);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("event")
    public Response createStep(NewStepEventRequest stepEventRequest) throws UnknownPlanException {
        LOG.info("Creating new plan step event " + stepEventRequest);
        planService.createStepEvent(stepEventRequest);
        return Response.ok().build();
    }
}
