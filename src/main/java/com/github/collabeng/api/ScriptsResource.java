package com.github.collabeng.api;

import com.github.collabeng.api.dto.ScriptDto;
import com.github.collabeng.api.requests.NewScriptRequest;
import com.github.collabeng.api.requests.UpdateScriptRequest;
import com.github.collabeng.api.responses.NamedEntityListResponse;
import com.github.collabeng.api.responses.NewEntityResponse;
import com.github.collabeng.services.ScriptService;
import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/api/script")
public class ScriptsResource {

    private static final Logger LOG = Logger.getLogger(ScriptsResource.class.getName());

    @Inject
    private ScriptService scriptService;



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public NewEntityResponse create(@Context HttpServletRequest request,
                                    NewScriptRequest newScriptRequest) {
        LOG.info("Creating Script " + newScriptRequest);

        return scriptService.createNewScript(newScriptRequest);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context HttpServletRequest request,
                                    NewScriptRequest updateScriptRequest) {
        LOG.info("Update Script " + updateScriptRequest);
        scriptService.updateScript(updateScriptRequest);

        return Response.ok().build();
    }

    @GET
    @Path("/summary/{match}/")
    @Produces(MediaType.APPLICATION_JSON)
    public NamedEntityListResponse listMatching(@Context HttpServletRequest request,
                                                @DefaultValue("") @PathParam("match") String match) {
        LOG.info("Summary List Script " + match);
        NamedEntityListResponse result = scriptService.listMatching(match);

        return result;
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public ScriptDto getByName(@Context HttpServletRequest request,
                                             @PathParam("name") String name) {
        LOG.info("Get Script " + name);
        ScriptDto script = scriptService.getScriptByName(name);

        return script;
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("test")
    public Response testScript(@Context HttpServletRequest request,
                           String script) {
        LOG.info("Running Script " + script);

        return Response.ok(scriptService.testScript(script)).build();
    }
}
