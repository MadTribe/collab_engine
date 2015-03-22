package com.github.collabeng.api.responses;

import java.util.Date;

/**
 * Created by paul.smout on 22/03/2015.
 */
public class NewPlanStepResponse {
    private final long id;
    private final Date createdAt;

    public NewPlanStepResponse(long id, Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
