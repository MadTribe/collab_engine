package com.github.collabeng.dao;

import com.github.collabeng.domain.PlanEntity;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlanDaoTest extends GenericPersistenceDaoTest {

    @Test
    public void should_create_an_user() {
        final PlanDao userDao = get(PlanDao.class);

        userDao.removeAll();
        userDao.persist(new PlanEntity());

        final List<PlanEntity> weChatUsers = userDao.findAll();
        assertThat(weChatUsers.size(), is(1));
    }
}