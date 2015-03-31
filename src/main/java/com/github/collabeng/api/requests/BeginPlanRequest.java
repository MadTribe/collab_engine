package com.github.collabeng.api.requests;

/**
 * Created by paul.smout on 23/03/2015.
 */
public class BeginPlanRequest {
    private Long id;

    public BeginPlanRequest() {
    }

    public BeginPlanRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
