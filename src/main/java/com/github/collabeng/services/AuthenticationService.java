package com.github.collabeng.services;

import com.github.collabeng.dao.SessionDao;
import com.github.collabeng.dao.UserDao;
import com.github.collabeng.domain.Encoding;
import com.github.collabeng.domain.SessionEntity;
import com.github.collabeng.domain.UserEntity;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import static java.lang.String.format;

public class AuthenticationService {
    @Inject
    private UserDao userDao;

    @Inject
    private SessionDao sessionDao;

    private static Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());

    @Transactional
    public Optional<SessionEntity> login(String username, String password){
        Optional<SessionEntity> ret = Optional.empty();
        Optional<UserEntity> user = userDao.findByUsername(username);

         if (user.isPresent()){
             if (checkPassword(user.get(), password)){
                ret = joinSession(user);
             }
         }

        return ret;
    }

    private Optional<SessionEntity> joinSession(Optional<UserEntity> user){


        LOGGER.info(format("Finding Session for user %s",user.get().getUsername()));

        Optional<SessionEntity> ret  = sessionDao.findBySessionUser(user.get());

        if (!ret.isPresent()){
            LOGGER.info(format("New Session for user %s",user.get().getUsername()));
            SessionEntity newSession = new SessionEntity(user.get(), UUID.randomUUID().toString());
            sessionDao.persist(newSession);
            ret = Optional.of(newSession);

        } else {
            LOGGER.info(format("Returning existing session for user %s",user.get().getUsername()));
        }

        return ret;
    }

    private boolean checkPassword(UserEntity userEntity, String password){

       return userEntity.getPassword().equals(hashPassword(password, userEntity.getSalt(), userEntity.getEncoding()));

    }

    private String hashPassword(String password, String salt, Encoding encoding) {
        String saltedPW = password + salt;

        if (encoding == Encoding.PLAIN){
          // do nothing
        } else if (encoding == Encoding.SHA256) {
            throw new NotImplementedException();
        }

        return saltedPW;
    }

}