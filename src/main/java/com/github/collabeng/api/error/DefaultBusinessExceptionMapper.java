package com.github.collabeng.api.error;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.invoke.MethodHandles;

/**
 * Created by paul.smout on 08/03/2015.
 */
@Provider
@Singleton
public class DefaultBusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Response toResponse(BusinessException e) {
        StringBuilder message = new StringBuilder(e.getMessage());

        Throwable err = e;

        while (err.getCause() != null){
            message.append(err.getCause().getMessage()).append(" : ");
            err = err.getCause();
        }


        return Response.status(e.getHttpCode()).entity(new ErrorMessage(message.toString())).build();
    }
}