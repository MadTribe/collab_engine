package com.github.collabeng.dao;

/**
 * Created by paul.smout on 26/01/2015.
 */
import com.github.collabeng.dao.queries.QueryParam;
import com.github.collabeng.domain.NamedOwnedEntity;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.github.collabeng.constants.Names.CURRENT_USER_ENTITY;
import static com.github.collabeng.dao.queries.QueryParam.of;
import static com.google.common.collect.Lists.asList;

@RequestScoped
public abstract class NamedOwnedDao<T extends NamedOwnedEntity> extends AccessControlDao<T> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Optional<T> findByName(String name){
        LOG.info("Find {} by name {}", this.getEntityClass().getSimpleName(), name);
        return queryOne(" o.name = :name", of("name", name));
    }

    public List<T> findByNamePrefix(String prefix){
        LOG.info("Find {} by prefix {}", this.getEntityClass().getSimpleName(), prefix);
        return queryMany(" o.name like :prefix", of("prefix", prefix));
    }
}
