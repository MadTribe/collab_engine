package com.github.collabeng.services;

import com.github.collabeng.constants.ContextAttributes;
import com.github.collabeng.dao.*;
import com.github.collabeng.domain.UserEntity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Logger;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;
import static com.mongodb.client.model.Filters.eq;

/**
 * The primary purpose of this Service is to delete all data for a user in integration tests.
 * But it will later allow users to clear their own data if they want to.
 */
public class UserTeardownService {
    private static final Logger LOG = Logger.getLogger(UserTeardownService.class.getName());

    @Inject
    @Named(CURRENT_USER_ENTITY)
    private Provider<UserEntity> currentUser;

    @Inject
    private TaskDao taskDao;

    @Inject
    private TaskContextDao taskContextDao;

    @Inject
    private PlanDao planDao;
    @Inject
    private PlanStepDao planStepDao;

    @Inject
    private PlanStepEventDao planStepEventDao;

    @Inject
    private ScriptDao scriptDao;

    @Inject
    private PlanStepEventParameterDao planStepEventParameterDao;

    @Inject
    private SessionDao sessionDao;

    @Inject
    private MongoDatabase mongoDatabase;

    @Transactional
    public void clearPersonalData(){
        taskDao.removeAll();

        planStepEventParameterDao.removeAll();
        planDao.clearMyFirstSteps();
        planStepEventDao.removeAll();
        scriptDao.removeAll();
        planStepDao.removeAll();
        planDao.removeAll();

        taskContextDao.removeAll();

        Long userId = currentUser.get().getId();
        mongoDatabase.getCollection(ContextAttributes.CONTEXTS_COLLECTION).deleteOne(eq(ContextAttributes.OWNER, userId));
    }

}