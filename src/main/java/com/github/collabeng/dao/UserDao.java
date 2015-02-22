package com.github.collabeng.dao;

import com.github.collabeng.domain.UserEntity;

import java.util.Optional;

import static com.github.collabeng.dao.util.QueryParam.of;

/**
 * Created by paul.smout on 26/01/2015.
 */
public class UserDao extends BaseDao<UserEntity> {
    public Optional<UserEntity> findByUsername(String username){
       return query("select distinct t from UserEntity t where t.username = :username",of("username", username));
    }
}
