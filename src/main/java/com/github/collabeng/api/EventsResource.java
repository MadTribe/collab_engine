package com.github.collabeng.api;

import com.github.collabeng.api.requests.EventMessage;
import com.github.collabeng.api.requests.NewEventParameter;
import com.github.collabeng.api.responses.BaseResponse;
import com.github.collabeng.api.responses.NewEntityResponse;
import com.github.collabeng.services.EventsService;
import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public BaseResponse taskEventReceived(@Context HttpServletRequest request,
                                          EventMessage eventMessage) {
        LOG.info(format("Event Received %s",request.toString()));
        BaseResponse response = eventsService.handleTaskEvent(eventMessage);

        LOG.info(response.toString());
        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{eventId}/params")
    public NewEntityResponse createEventParameter(@Context HttpServletRequest request,
                                         @PathParam("eventId") long eventId,
                                         NewEventParameter newEventParameter) {
        return eventsService.createEventParameter(eventId, newEventParameter);
    }


}
