package com.github.collabeng.api.responses;

import org.eclipse.jetty.security.LoginService;

/**
 * Created by paul.smout on 20/02/2015.
 */
public class LoginSuccess extends BaseResponse {

    private String sessionId;

    public LoginSuccess(){
    }

    public LoginSuccess(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
