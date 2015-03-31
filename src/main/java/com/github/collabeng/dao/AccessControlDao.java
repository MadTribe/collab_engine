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
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.invoke.MethodHandles;
import java.util.*;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;
import static com.google.common.collect.Lists.asList;

@RequestScoped
public abstract class AccessControlDao<T extends OwnedEntity> extends BaseDao<T> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Inject
    @Named(CURRENT_USER_ENTITY)
    private Provider<UserEntity> currentUser;


    @Override
    public Optional<T> find(Long id) {
        LOG.info("Finding {} by id {}", getEntityClass(), id);

        return super.find(id);
    }

    @Override
    public List<T> findAll() {
        return query("select o from " + getEntityClass().getCanonicalName() + " " + ALIAS + " where " + ALIAS + ".owner = :currentUser ", param("currentUser", currentUser.get())).getResultList();
    }

    @Override
    protected <Y> int nativeUpdate(String updateClasue, String condition, Collection<QueryParam<Y>> queryParams) {
        String owned = "  " + ALIAS + ".owner_ID = :currentUser " + condition;
        Collection safeParams = new ArrayList<QueryParam<Y>>();
        safeParams.add(param("currentUser",currentUser.get()));
        return super.nativeUpdate(updateClasue, owned, safeParams);
    }

    @Override
    protected Optional<T> queryOne(String queryStr, QueryParam<?> param, QueryParam<?>... params) {
        Optional<T> ret = Optional.empty();
        try {
            TypedQuery<T> query = getTypedQuery(queryStr, param, params);
            ret = Optional.of(query.getSingleResult());
        } catch (PersistenceException e){
            LOG.error("Error Retrieving Row");
        }

        return ret;
    }

    protected List<T> queryMany(String queryStr, QueryParam<?> param, QueryParam<?>... params) {
        List<T> ret;

        TypedQuery<T> query = getTypedQuery(queryStr, param, params);
        ret = query.getResultList();

        return ret;
    }

    private TypedQuery<T> getTypedQuery(String queryStr, QueryParam<?> param, QueryParam<?>[] params) {
        TypedQuery<T> query = getEntityManager().createQuery("select o from " + getEntityClass().getCanonicalName() + " o where o.owner = :currentUser and " + queryStr, getEntityClass());

        query.setParameter("currentUser",currentUser.get());
        asList(param, params).stream().forEach(oneParam -> query.setParameter(oneParam.getName(), oneParam.getValue()));
        return query;
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
