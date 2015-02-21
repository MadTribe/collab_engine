package com.github.collabeng.api;

import com.github.collabeng.api.requests.LoginRequest;
import com.github.collabeng.api.requests.NewPlanRequest;
import com.github.collabeng.api.responses.LoginSuccess;
import com.github.collabeng.domain.SessionEntity;
import com.github.collabeng.services.AuthenticationService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;

/**
 * Created by paul.smout on 20/02/2015.
 */
@Path("/api/login")
public class LoginResource {

    private static final String USER_SESSION_ID = "session_id";
    @Inject
    private AuthenticationService authenticationService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context HttpServletRequest request,
                           LoginRequest login) {

        Optional<SessionEntity> sessOptional =  authenticationService.login(login.getUserName(),login.getPassword());

        Response response = Response.status(Response.Status.FORBIDDEN).build();
        if (sessOptional.isPresent()){

             response = Response.ok().entity(new LoginSuccess(sessOptional.get().getKey()))
                    .cookie(new NewCookie(new Cookie(USER_SESSION_ID, sessOptional.get().getKey(), "/", null)))
                    .build();
        }

        return response;


    }
}
