package com.github.collabeng.injection;

import com.github.collabeng.configuration.CollaborationEngineConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.setup.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

public final class GuiceInitializer extends GuiceServletContextListener {



    private Injector instance;
    private static final Logger LOG = Logger.getLogger(GuiceInitializer.class.getName());
    private final String persistenceUnit;
    private final Environment environment;
    private final CollaborationEngineConfiguration configuration;

    public GuiceInitializer(final String persistenceUnit,  Environment environment, final CollaborationEngineConfiguration configuration) {
        this.persistenceUnit = persistenceUnit;
        this.environment = environment;
        this.configuration = configuration;


        PersistenceProviderResolverHolder.setPersistenceProviderResolver(new PersistenceProviderResolver() {
            private final List<PersistenceProvider> providers_ = asList((PersistenceProvider) new HibernatePersistenceProvider());

            @Override
            public List<PersistenceProvider> getPersistenceProviders() {
                return providers_;
            }

            @Override
            public void clearCachedProviders() {
            }
        });


        ManagedDataSource managedDataSource = null;
        try {
            managedDataSource = configuration.getDatabase().build(environment.metrics(), "mysql");
        } catch (ClassNotFoundException e) {
            new RuntimeException("Startup Failed Because Mysql not found ", e);
        }

        MongoModule mongoModule = new MongoModule(configuration.getMongoConfiguration());

        JpaPersistModule persistModule = new JpaPersistModule(persistenceUnit);
        Properties connectionProperties = new Properties();

        connectionProperties.put(org.hibernate.cfg.AvailableSettings.DATASOURCE, managedDataSource);
        persistModule.properties(connectionProperties);

        final Injector persistenceInjector = Guice.createInjector(persistModule);


        final CollaborationModule collaborationModule = new CollaborationModule(persistenceInjector, configuration);
        instance = persistenceInjector.createChildInjector(mongoModule).createChildInjector(collaborationModule).createChildInjector(new AppServletModule());
    }

    @Override
    protected Injector getInjector() {

        return instance;
    }

    public  <T> T get(Class<T> clazz) {
        return instance.getInstance(clazz);
    }


}
