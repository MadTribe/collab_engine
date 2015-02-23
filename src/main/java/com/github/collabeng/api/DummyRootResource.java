package com.github.collabeng.api;

import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.services.PlanService;
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

@Path("/dummy/root")
public class DummyRootResource {

    private static final Logger LOG = Logger.getLogger(DummyRootResource.class.getName());
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@Context HttpServletRequest request) {
        return Response.ok().build();
    }

}
