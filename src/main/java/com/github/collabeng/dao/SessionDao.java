package com.github.collabeng.dao;

import com.github.collabeng.domain.SessionEntity;
import com.github.collabeng.domain.UserEntity;

import java.util.Optional;

import static com.github.collabeng.dao.util.QueryParam.of;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class SessionDao extends BaseDao<SessionEntity> {
    public Optional<SessionEntity> findBySessionKey(String key){
       return query("select distinct t from SessionEntity t where t.key = :key",of("key", key));
    }

    public Optional<SessionEntity> findBySessionUser(UserEntity user){
        return query("select distinct t from SessionEntity t where t.user = :user",of("user", user));
    }
}
