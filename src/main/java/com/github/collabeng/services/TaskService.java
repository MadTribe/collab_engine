package com.github.collabeng.services;

import com.github.collabeng.api.dto.TaskDto;
import com.github.collabeng.dao.TaskDao;
import com.github.collabeng.domain.Task;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

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