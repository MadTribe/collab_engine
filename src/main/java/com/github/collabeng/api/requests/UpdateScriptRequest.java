package com.github.collabeng.api.requests;

/**
 * Created by paul.smout on 18/04/2015.
 */
public class UpdateScriptRequest extends NewScriptRequest {
    private Long id;

    public UpdateScriptRequest() {
    }

    public Long getId() {
        return id;
    }
}
