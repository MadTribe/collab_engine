package com.github.collabeng.api;

import com.github.collabeng.dao.SessionDao;
import com.github.collabeng.domain.SessionEntity;
import static com.github.collabeng.constants.Names.*;
import com.google.common.base.Preconditions;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by paul.smout on 22/02/2015.
 */
@Singleton
public class UserIdentityFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(UserIdentityFilter.class.getName());

    @Inject
    private SessionDao sessionDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Filtering Request ");
        Preconditions.checkArgument(servletRequest instanceof HttpServletRequest);
        Preconditions.checkArgument(servletResponse instanceof HttpServletResponse);
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Optional<String> sessionKey = getSessionKey(httpRequest);

        if (sessionKey.isPresent()){

            Optional<SessionEntity> session = sessionDao.findBySessionKey(sessionKey.get());

            if (session.isPresent()){
                LOG.info("Session found " + session.get());
                httpRequest.setAttribute(
                        Key.get(Integer.class, Names.named(CURRENT_USER_ENTITY)).toString(),
                        session.get().getUser());
                filterChain.doFilter(httpRequest, servletResponse);

            } else {
                notAuthorised(httpServletResponse);
            }

        } else {
            notAuthorised(httpServletResponse);
        }


    }

    private void notAuthorised(HttpServletResponse response) {
        try {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } catch (IOException e) {
            LOG.info("Error Sending Response"); // TODO
        }
    }

    private Optional<String> getSessionKey(HttpServletRequest httpRequest) {
        Optional<String> ret = Optional.empty();
        for (Cookie cookie : httpRequest.getCookies()){
            if (cookie.getName().equals(USER_SESSION_ID)){
                ret = Optional.ofNullable(cookie.getValue());
            }
        }

        return ret;
    }


    @Override
    public void destroy() {

    }
}
