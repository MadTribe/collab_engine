package com.github.collabeng.injection;

import com.github.collabeng.api.*;
import com.github.collabeng.api.error.DefaultBusinessExceptionMapper;
import com.github.collabeng.api.error.DefaultPersistenceExceptionMapper;
import com.github.collabeng.domain.UserEntity;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;

/**
 * Created by paul.smout on 21/02/2015.
 */
public class AppServletModule extends ServletModule {
    private static final Logger LOG = Logger.getLogger(AppServletModule.class.getName());

    @Override
    protected void configureServlets() {
        LOG.info("Configure Guice Servlets");
        bind(GuiceContainer.class);

        filter("/*").through(PersistFilter.class);
        filter("/api/*").through(UserIdentityFilter.class);

        PackagesResourceConfig resourceConfig = new PackagesResourceConfig("jersey.resources.package");
        for (Class<?> resource : resourceConfig.getClasses()) {
            bind(resource);
        }
        bind(TaskResource.class);
        bind(PlanResource.class);
        bind(LoginResource.class);
        bind(TeardownResource.class);
        bind(EventsResource.class);
        bind(ScriptsResource.class);

        bind(DefaultBusinessExceptionMapper.class);
        bind(DefaultPersistenceExceptionMapper.class);

        serve( "/*" ).with( GuiceContainer.class );
    }

    @Provides
    @Named("current-user")
    @RequestScoped
    @Inject
    UserEntity provideUserId(HttpServletRequest request) {
        UserEntity ret = (UserEntity)request.getAttribute(Key.get(Integer.class, Names.named(CURRENT_USER_ENTITY)).toString());
        return ret;
    }
}
