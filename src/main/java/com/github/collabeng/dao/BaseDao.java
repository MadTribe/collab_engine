package com.github.collabeng.dao;

/**
 * Created by paul.smout on 26/01/2015.
 */

import com.github.collabeng.dao.util.QueryParam;
import com.github.collabeng.domain.BaseEntity;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.asList;
import static java.lang.String.format;
import static java.util.Optional.*;


public abstract class BaseDao<T extends BaseEntity> {

    @Inject
    private Provider<EntityManager> entityManager;

    private Class<T> entityClass;

    private static final Logger LOG = Logger.getLogger(BaseDao.class.getName());

    @SuppressWarnings("unchecked")
    public BaseDao() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            this.entityClass = (Class<T>) ((ParameterizedType) ((Class) getClass()
                    .getGenericSuperclass()).getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager.get();
    }

    public Optional<T> find(Long id) {
        return ofNullable(getEntityManager().find(entityClass, id));
    }


    public List<T> findAll() {
        final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
        query.from(entityClass);

        return getEntityManager().createQuery(query).getResultList();
    }

    protected javax.persistence.TypedQuery<T> query(String queryStr) {
        return getEntityManager().createQuery(queryStr, entityClass);
    }

    protected Optional<T> query(String queryStr, QueryParam<? extends Object> param, QueryParam<? extends Object>... params) {
        final TypedQuery<T> query = getEntityManager().createQuery(queryStr, entityClass);
        asList(param, params).stream().forEach(oneParam -> query.setParameter(oneParam.getName(), oneParam.getValue()));
        final List<T> resultList = query.getResultList();
        return resultList.isEmpty() ? empty() : of(resultList.get(0));
    }

    public void persist(T entity) {
        LOG.info("Persisting Entity " + getEntityManager().getTransaction().isActive());
        LOG.info("Persisting Entity " + entityClass.getCanonicalName());
        getEntityManager().persist(entity);
        LOG.info("Finished Persisting Entity " + entityClass.getCanonicalName());
    }

    public void flush() {
      //  getEntityManager();
      //  getEntityManager().flush();

    }

    public void persist(Iterable<T> entities) {
        for (T entity : entities) {
            getEntityManager().persist(entity);
        }
    }

    public T merge(T entity) {
        return getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    public Optional<T> getReference(Long id) {
        return ofNullable(getEntityManager().getReference(entityClass, id));
    }

    public long count() {
        final Query q = getEntityManager().createQuery(
                format("select count(t) from %s t", entityClass.getSimpleName()));
        return (Long) q.getSingleResult();
    }

    public void removeAll() {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaDelete criteriaDelete = criteriaBuilder.<T>createCriteriaDelete(entityClass);
        criteriaDelete.from(entityClass);


        int result = getEntityManager().createQuery(criteriaDelete).executeUpdate();
        LOG.info("delete @" + result);

    }

    protected QueryParam<T> param(String name, T value){
        return new QueryParam<>(name,value);
    }
}
