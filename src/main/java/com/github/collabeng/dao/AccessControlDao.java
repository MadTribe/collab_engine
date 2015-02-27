package com.github.collabeng.dao;

/**
 * Created by paul.smout on 26/01/2015.
 */

import com.github.collabeng.domain.OwnedEntity;

import java.util.logging.Logger;

import static com.google.common.collect.Lists.asList;
import static java.lang.String.format;


public abstract class AccessControlDao<T extends OwnedEntity> extends BaseDao<T>{

    private static final Logger LOG = Logger.getLogger(AccessControlDao.class.getName());

    // TODO add in access controlled versions of super methods
    // TODO listAll should add current owner as where criteria. [t:1h]
    // TODO work out how to share query criteria between this and BaseDao (Decorator maybe)
    // TODO this class should insert owner on create, rather than the service layer.
}
