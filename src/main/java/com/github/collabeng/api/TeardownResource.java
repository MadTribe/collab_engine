package com.github.collabeng.api;

import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.services.PlanService;
import com.github.collabeng.services.UserTeardownService;
import com.google.inject.Inject;

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

@Path("/api/teardown")
public class TeardownResource {

    private static final Logger LOG = Logger.getLogger(TeardownResource.class.getName());

    @Inject
    private UserTeardownService teardownService;



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context HttpServletRequest request) {
        LOG.info("Trying to delete all user data");
        teardownService.clearPersonalData();
        return Response.ok().build();
    }
}
