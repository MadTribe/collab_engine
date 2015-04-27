package com.github.collabeng.api.responses;

/**
 * Created by paul.smout on 25/04/2015.
 */
public class ScriptResponse extends BaseResponse{
    private Object resp;

    public ScriptResponse() {
    }

    public ScriptResponse(Object resp) {
        this.resp = resp;
    }

    public Object getResp() {
        return resp;
    }
}
