package com.github.collabeng.dao;

/**
 * Created by paul.smout on 26/01/2015.
 */

import com.github.collabeng.dao.util.QueryParam;
import com.github.collabeng.domain.OwnedEntity;
import com.github.collabeng.domain.PlanEntity;
import com.github.collabeng.domain.UserEntity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;
import static com.google.common.collect.Lists.asList;
import static java.lang.String.format;


public abstract class AccessControlDao<T extends OwnedEntity> extends BaseDao<T> {

    private static final Logger LOG = Logger.getLogger(AccessControlDao.class.getName());

    @Inject
    @Named(CURRENT_USER_ENTITY)
    private Provider<UserEntity> currentUser;

    // TODO add in access controlled versions of super methods
    // TODO listAll should add current owner as where criteria. [t:1h]
    // TODO work out how to share query criteria between this and BaseDao (Decorator maybe)
    // TODO this class should insert owner on create, rather than the service layer.


    @Override
    public Optional<T> find(Long id) {
        return super.find(id);
    }

    @Override
    public List<T> findAll() {
        return super.findAll();
    }

    @Override
    protected TypedQuery<T> query(String queryStr) {
        return super.query(queryStr);
    }

    @Override
    protected Optional<T> query(String queryStr, QueryParam<?> param, QueryParam<?>... params) {
        return super.query(queryStr, param, params);
    }

    @Override
    public T persist(T entity) {
        entity.setOwner(currentUser.get());
        return super.persist((T)entity);
    }

    @Override
    public void flush() {
        super.flush();
    }

    @Override
    public void persist(Iterable<T> entities) {
        Collection<T> owned = new ArrayList<T>();

        for (T entity : entities) {
            persist(entity);
        }
    }

    @Override
    public T merge(T entity) {
        return super.merge(entity);
    }

    @Override
    public void remove(T entity) {
        super.remove(entity);
    }


    @Override
    public long count() {
        return super.count();
    }


}
