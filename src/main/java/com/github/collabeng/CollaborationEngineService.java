package com.github.collabeng;

import com.github.collabeng.api.DummyRootResource;
import com.github.collabeng.api.LoginResource;
import com.github.collabeng.api.PlanResource;
import com.github.collabeng.api.UserIdentityFilter;
import com.github.collabeng.injection.GuiceInitializer;
import com.google.inject.servlet.GuiceFilter;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by paul.smout on 25/01/2015.
 */
public class CollaborationEngineService extends Application<CollaborationEngineConfiguration> {
    public static void main(String[] args) throws Exception {
       new CollaborationEngineService().run(args);
    }


    @Override
    public String getName() {
        return "Collaboration Engine";
    }

    @Override
    public void initialize(Bootstrap<CollaborationEngineConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<CollaborationEngineConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(CollaborationEngineConfiguration configuration) {
                return configuration.getDatabase();
            }
        });

    }

    @Override
    public void run(CollaborationEngineConfiguration configuration, Environment environment) throws Exception {
        GuiceInitializer guiceMain = new GuiceInitializer("persistenceUnit", environment, configuration);

        FilterRegistration.Dynamic guiceFilter = environment.servlets().addFilter("GuiceFilter",GuiceFilter.class);
        guiceFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        environment.servlets().addServletListeners(guiceMain);


        // NB other filters and resources are set up in the Guice AppServletModule
//
   //     environment.jersey().register(guiceMain.get(PlanResource.class));
          environment.jersey().register(guiceMain.get(DummyRootResource.class));

    }
}
