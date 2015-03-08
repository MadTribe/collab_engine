package com.github.collabeng.api.error;

import javax.ws.rs.core.Response;

/**
 * Created by paul.smout on 08/03/2015.
 */
public class BusinessException extends Exception{
    public Response.Status getHttpCode() {
        return Response.Status.BAD_REQUEST;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
