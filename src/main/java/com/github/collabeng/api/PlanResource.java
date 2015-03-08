package com.github.collabeng.api;

import com.github.collabeng.api.error.UnknownPlanException;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
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
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

@Path("/api/plan")
public class PlanResource {

    private static final Logger LOG = Logger.getLogger(PlanResource.class.getName());

    @Inject
    private PlanService planService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanEntity> list(@Context HttpServletRequest request) {
        final List<PlanEntity> collect = planService.listAll();
        return collect;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context HttpServletRequest request,
                                         NewPlanRequest newPlan) {
        LOG.info("" + newPlan);
        planService.createPlan(newPlan);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{planId}/step")
    public Response createStep(@Context HttpServletRequest request,
                               @PathParam("planId") long planId,
                                NewPlanStepRequest newPlanStep) throws UnknownPlanException {
        LOG.info("Creating new plan step " + newPlanStep);
        planService.createPlanStep(newPlanStep, planId);
        return Response.ok().build();
    }
}
