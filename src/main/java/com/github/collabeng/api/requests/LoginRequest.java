package com.github.collabeng.api.requests;

/**
 * Created by paul.smout on 20/02/2015.
 */
public class LoginRequest {
    private String userName;
    private String password;

    public LoginRequest(){
    }

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
