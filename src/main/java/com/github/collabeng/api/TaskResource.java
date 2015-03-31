package com.github.collabeng.api;

import com.github.collabeng.api.dto.TaskDto;
import com.github.collabeng.services.PlanService;
import com.github.collabeng.services.TaskService;
import com.google.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/api/task")
public class TaskResource {

    private static final Logger LOG = Logger.getLogger(TaskResource.class.getName());

    @Inject
    private TaskService taskService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskDto> list(@Context HttpServletRequest request) {
        return taskService.listAll();
    }


}
