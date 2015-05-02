package com.github.collabeng.api.dto;

import com.github.collabeng.domain.PlanStepEventEntity;
import com.github.collabeng.domain.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul.smout on 23/03/2015.
 */
public class TaskDto {
    private long id;
    private String name;
    private String description;
    private List<PlanStepEvent> events = new ArrayList<>();

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();

        List<PlanStepEventEntity> events = task.getPlanStep().getKnownPossibleEvents();
        for (PlanStepEventEntity eventEntity : events){
            this.events.add(new PlanStepEvent(eventEntity));
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<PlanStepEvent> getEvents() {
        return events;
    }
}
