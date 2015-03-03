package com.github.collabeng.dao;

import com.github.collabeng.domain.UserEntity;
import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class BaseDaoTest {
    protected EntityManager entityManager;
    private  Injector injector;

    private UserEntity currentUser;

    protected <T> T get(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    private PersistServiceInitializer startPersistService() {
        return get(PersistServiceInitializer.class);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println(">>>>> BaseDao Setup");
        synchronized (this) {
            if (injector == null) {
                injector = Guice.createInjector(new JpaPersistModule("utPersistenceUnit"), new TestConfigModule());
                startPersistService();
                entityManager = get(EntityManager.class);
            }
        }

        entityManager.getTransaction().begin();
            entityManager.getTransaction().setRollbackOnly();

    }

    @After
    public void teardown() {
        currentUser = null;
        entityManager.getTransaction().rollback();
    }

    protected void flushDataInDB() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.flush();
        }
        entityManager.clear();
    }

    protected void removeAll(Class<? extends BaseDao>... daoClasses) {
        for (Class<? extends BaseDao> daoClass : daoClasses) {
            get(daoClass).removeAll();
        }
    }

    protected void setCurrentUser(UserEntity currentUser){
        this.currentUser = currentUser;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    static class PersistServiceInitializer {
        @Inject
        PersistServiceInitializer(PersistService service) {
            service.start();
            // At this point JPA is started and ready.
        }
    }

    class TestConfigModule implements Module {

        @Override
        public void configure(Binder binder) {

        }

        @Provides
        @Named("current-user")
        UserEntity provideUserId() {

            return currentUser;
        }


    }
}
