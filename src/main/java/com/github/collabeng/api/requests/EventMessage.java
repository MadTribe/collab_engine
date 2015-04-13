package com.github.collabeng.api.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul.smout on 29/03/2015.
 */
public class EventMessage {
    private Long taskId;
    private String eventName;
    private Map<String, String> params = new HashMap<>();

    public EventMessage() {
    }

    public EventMessage(Long taskId, String eventName, Map<String, String> params) {
        this.taskId = taskId;
        this.eventName = eventName;
        this.params = params;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getEventName() {
        return eventName;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "taskId=" + taskId +
                ", eventName='" + eventName + '\'' +
                ", params=" + params +
                '}';
    }
}

