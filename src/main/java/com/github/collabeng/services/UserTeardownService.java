package com.github.collabeng.services;

import com.github.collabeng.dao.PlanDao;
import com.github.collabeng.dao.PlanStepDao;
import com.github.collabeng.dao.SessionDao;
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
    private PlanDao planDao;
    @Inject
    private PlanStepDao planStepDao;


    @Inject
    private SessionDao sessionDao;

    @Transactional
    public void clearPersonalData(){

        this.planStepDao.removeAll();
        this.planDao.removeAll();

    }

}