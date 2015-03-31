package com.github.collabeng.api.requests;

/**
 * Created by paul.smout on 29/03/2015.
 */
public class EventMessage {
    private Long taskId;
    private String eventName;

    public EventMessage() {
    }

    public EventMessage(Long taskId, String eventName) {
        this.taskId = taskId;
        this.eventName = eventName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getEventName() {
        return eventName;
    }

}

