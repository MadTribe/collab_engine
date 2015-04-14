package com.github.collabeng.api;

import com.github.collabeng.services.ScriptService;
import com.github.collabeng.services.UserTeardownService;
import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/api/script")
public class ScriptRunnerResource {

    private static final Logger LOG = Logger.getLogger(ScriptRunnerResource.class.getName());

    @Inject
    private ScriptService scriptService;



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context HttpServletRequest request,
                           String script) {
        LOG.info("Running Script " + script);

        return Response.ok(scriptService.runScript(script)).build();
    }
}
