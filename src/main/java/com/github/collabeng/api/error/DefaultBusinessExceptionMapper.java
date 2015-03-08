package com.github.collabeng.api.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.lang.invoke.MethodHandles;

/**
 * Created by paul.smout on 08/03/2015.
 */
public class DefaultBusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Response toResponse(BusinessException e) {

        return Response.status(e.getHttpCode()).entity(e.getMessage()).build();
    }
}