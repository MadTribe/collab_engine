package com.github.collabeng.injection;

import com.github.collabeng.CollaborationEngineConfiguration;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;
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

public final class GuiceInitializer {
    private static Injector instance;
    private static final Logger LOG = Logger.getLogger(GuiceInitializer.class.getName());

    private GuiceInitializer() {
    }

    public synchronized static void init(final String persistenceUnit,  Environment environment, final CollaborationEngineConfiguration configuration) throws ClassNotFoundException {
        if (instance != null) {
            return;
        }

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


        ManagedDataSource managedDataSource = configuration.getDatabase().build(environment.metrics(), "mysql");



        JpaPersistModule persistModule = new JpaPersistModule(persistenceUnit);
        Properties connectionProperties = new Properties();

        connectionProperties.put(org.hibernate.cfg.AvailableSettings.DATASOURCE, managedDataSource);
        persistModule.properties(connectionProperties);

        final Injector persistenceInjector = Guice.createInjector(new AppServletModule(),persistModule);



        //start the persist service
        persistenceInjector.getInstance(PersistServiceInitializer.class);

        final CollaborationModule collaborationModule = new CollaborationModule(persistenceInjector, configuration);
        instance = persistenceInjector.createChildInjector(collaborationModule);
    }

    public static <T> T get(Class<T> clazz) {
        return instance.getInstance(clazz);
    }

    public static class PersistServiceInitializer {
        @Inject
        PersistServiceInitializer(PersistService service) {
            LOG.info("Starting JPA SERVICE");
            service.start();
            // At this point JPA is started and ready.
        }
    }
}
