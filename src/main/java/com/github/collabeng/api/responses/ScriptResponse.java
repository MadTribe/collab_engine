package com.github.collabeng.api.responses;

/**
 * Created by paul.smout on 25/04/2015.
 */
public class ScriptResponse extends BaseResponse{
    private String resp;

    public ScriptResponse() {
    }

    public ScriptResponse(String resp) {
        this.resp = resp;
    }

    public String getResp() {
        return resp;
    }
}
