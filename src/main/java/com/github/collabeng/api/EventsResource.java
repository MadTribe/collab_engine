package com.github.collabeng.api;

import com.github.collabeng.api.dto.TaskDto;
import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.services.EventsService;
import com.github.collabeng.services.TaskService;
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

import static java.lang.String.format;

@Path("/api/events")
public class EventsResource {

    private static final Logger LOG = Logger.getLogger(EventsResource.class.getName());

    @Inject
    private EventsService eventsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("task")
    public Response taskEventReceived(@Context HttpServletRequest request,
                                  EventMessage eventMessage) {
        LOG.info(format("Event Received %s",request.toString()));
        eventsService.handleTaskEvent(eventMessage);

        return Response.ok().build();
    }


}
