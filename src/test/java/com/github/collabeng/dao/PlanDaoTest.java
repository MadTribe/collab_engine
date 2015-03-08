package com.github.collabeng.dao;

import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.UserEntity;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PlanDaoTest extends BaseDaoTest {

    @Test
    public void should_create_a_plan() {
        final PlanDao planDao = get(PlanDao.class);
        final UserDao userDao = get(UserDao.class);

        UserEntity userEntity = userDao.persist(new UserEntity());

        planDao.removeAll();

        setCurrentUser(userEntity);

        planDao.persist(new PlanEntity("name1","desc1"));
        planDao.persist(new PlanEntity("name2","desc1"));
        planDao.persist(new PlanEntity("name3","desc1"));
        planDao.persist(new PlanEntity("name4","desc1"));


        final List<PlanEntity> plans = planDao.findAll();
        assertThat(plans.size(), is(4));

        for (PlanEntity plan : plans){
            assertThat(plan.getOwner(), equalTo(userEntity));
        }


    }

    @Test
    public void should_edit_a_plan() {
        final PlanDao planDao = get(PlanDao.class);
        final UserDao userDao = get(UserDao.class);
        assertThat(planDao.count(),is(0L));

        UserEntity userEntity = userDao.persist(new UserEntity("paul","lalal","123",true));

        setCurrentUser(userEntity);

        PlanEntity planEntity = planDao.persist(new PlanEntity("name1","desc1"));

        assertThat(planDao.count(),is(1L));

        planDao.merge(planEntity.withName("name2"));

        assertThat(planDao.count(),is(1L));

        planDao.merge(planEntity.withName("name3"));
        assertThat(planDao.count(),is(1L));

        planDao.merge(planEntity.withName("name4"));
        assertThat(planDao.count(),is(1L));

        planDao.merge(planEntity.withName("name5"));
        assertThat(planDao.count(),is(1L));

        Optional<PlanEntity> result = planDao.find(planEntity.getId());
        assertThat(result.get().getName(), equalTo("name5"));
        assertThat(result.get().getVersion(), equalTo(4L));
        assertThat(result.get().getOwner(), equalTo(userEntity));



        planDao.removeAll();

        assertThat(planDao.count(),is(0L));

    }
}