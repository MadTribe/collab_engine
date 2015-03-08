package com.github.collabeng.dao;

/**
 * Created by paul.smout on 26/01/2015.
 */

import com.github.collabeng.dao.queries.QueryParam;
import com.github.collabeng.domain.OwnedEntity;
import com.github.collabeng.domain.UserEntity;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;
import static com.google.common.collect.Lists.asList;
import static java.lang.String.format;

@RequestScoped
public abstract class AccessControlDao<T extends OwnedEntity> extends BaseDao<T> {

    private static final Logger LOG = Logger.getLogger(AccessControlDao.class.getName());

    @Inject
    @Named(CURRENT_USER_ENTITY)
    private Provider<UserEntity> currentUser;

    // TODO add in access controlled versions of super methods
    // TODO listAll should add current owner as where criteria. [t:1h]
    // TODO work out how to share queryOne criteria between this and BaseDao (Decorator maybe)
    // TODO this class should insert owner on create, rather than the service layer.


    @Override
    public Optional<T> find(Long id) {
        return super.find(id);
    }

    @Override
    public List<T> findAll() {
        return query("select o from " + getEntityClass().getCanonicalName() + " o where o.owner = :currentUser ", param("currentUser", currentUser.get())).getResultList();
    }

    @Override
    protected TypedQuery<T> query(String queryStr,  QueryParam<? extends Object> param, QueryParam<? extends Object>... params ) {
        return super.query(queryStr, param, params);
    }

    @Override
    protected Optional<T> queryOne(String queryStr, QueryParam<?> param, QueryParam<?>... params) {
         return null;
    }


    @Override
    public T persist(T entity) {
        return super.persist((T)entity.withOwner(currentUser.get()));
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
    public void removeAll() {
        Query query = getEntityManager().createQuery("delete from " + getEntityClass().getCanonicalName() + " o where o.owner = :currentUser ");
        query.setParameter("currentUser",currentUser.get());
        query.executeUpdate();
    }

    @Override
    public long count() {
        return super.count();
    }


}
