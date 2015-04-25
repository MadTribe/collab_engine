package com.github.collabeng.services;

import com.github.collabeng.dao.*;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import java.util.logging.Logger;

/**
 * The primary purpose of this Service is to delete all data for a user in integration tests.
 * But it will later allow users to clear their own data if they want to.
 */
public class UserTeardownService {
    private static final Logger LOG = Logger.getLogger(UserTeardownService.class.getName());

    @Inject
    private TaskDao taskDao;

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

    @Transactional
    public void clearPersonalData(){
        taskDao.removeAll();

        planStepEventParameterDao.removeAll();
        planDao.clearMyFirstSteps();
        planStepEventDao.removeAll();
        scriptDao.removeAll();
        planStepDao.removeAll();
        planDao.removeAll();

    }

}