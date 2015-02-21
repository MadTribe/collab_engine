package com.github.collabeng.injection;


import com.github.collabeng.CollaborationEngineConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;

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
