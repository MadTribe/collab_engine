package com.github.collabeng;

import com.github.collabeng.api.LoginResource;
import com.github.collabeng.api.PlanResource;
import com.github.collabeng.injection.GuiceInitializer;
import com.google.inject.servlet.GuiceFilter;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        GuiceInitializer.init("persistenceUnit", environment, configuration);

        environment.jersey().register(GuiceFilter.class);

        environment.jersey().register(GuiceInitializer.get(PlanResource.class));
        environment.jersey().register(GuiceInitializer.get(LoginResource.class));

    }
}
