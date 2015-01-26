package com.github.collabeng.injection;


import com.github.collabeng.CollaborationEngineConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.sun.jersey.api.client.Client;

public class CollaborationModule extends AbstractModule {
    private final Injector persistenceInjector;
    private final CollaborationEngineConfiguration configuration;

    public CollaborationModule(final Injector persistenceInjector, final CollaborationEngineConfiguration configuration) {
        this.persistenceInjector = persistenceInjector;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(CollaborationEngineConfiguration.class).toInstance(configuration);
       // bindListener(Matchers.any(), new SLF4JTypeListener());


    }


}
