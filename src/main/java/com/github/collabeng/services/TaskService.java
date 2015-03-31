package com.github.collabeng.services;

import com.github.collabeng.api.dto.PlanDto;
import com.github.collabeng.api.dto.PlanStepDto;
import com.github.collabeng.api.dto.PlanSummaryDto;
import com.github.collabeng.api.dto.TaskDto;
import com.github.collabeng.api.error.PlanNotFoundException;
import com.github.collabeng.api.error.UnknownPlanException;
import com.github.collabeng.api.error.UnknownPlanStepException;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.requests.NewPlanStepRequest;
import com.github.collabeng.api.requests.NewStepEventRequest;
import com.github.collabeng.api.responses.NewPlanStepResponse;
import com.github.collabeng.dao.PlanDao;
import com.github.collabeng.dao.PlanStepDao;
import com.github.collabeng.dao.PlanStepEventDao;
import com.github.collabeng.dao.TaskDao;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.PlanStepEntity;
import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.Task;
import com.github.collabeng.eventvalidators.DefaultValidators;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.google.inject.servlet.RequestScoped;
import com.sun.javafx.tk.Toolkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RequestScoped
public class TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    private Provider<TaskDao> taskDaoProvider;


    public TaskService() {
    }

    public List<TaskDto> listAll(){
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = this.taskDaoProvider.get().allOpen();
        LOG.info("found {} tasks.", tasks.size());
        tasks.forEach(task -> taskDtos.add(new TaskDto(task.getId(), task.getName(), task.getDescription())));
        return taskDtos;
    }


}