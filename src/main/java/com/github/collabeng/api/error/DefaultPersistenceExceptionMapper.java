package com.github.collabeng.api.error;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.invoke.MethodHandles;

/**
 * Created by paul.smout on 08/03/2015.
 */
@Provider
@Singleton
public class DefaultPersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Response toResponse(PersistenceException e) {
        String message = e.getMessage();

        if (e.getCause() != null){
            message += e.getCause().getMessage();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(message)).build();
    }
}