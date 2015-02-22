package com.github.collabeng.dao;

import com.google.inject.Guice;
import com.google.inject.Injector;
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
    private static Injector injector;

    protected <T> T get(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    private PersistServiceInitializer startPersistService() {
        return get(PersistServiceInitializer.class);
    }

    @Before
    public void setUp() throws Exception {
        synchronized (this) {
            if (injector == null) {
                injector = Guice.createInjector(new JpaPersistModule("utPersistenceUnit"));
                startPersistService();
                entityManager = get(EntityManager.class);
            }
        }
        entityManager.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    protected void commitTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
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
}
