package com.github.collabeng.api;

import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.services.PlanService;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
