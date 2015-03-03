package com.github.collabeng.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Created by paul.smout on 26/01/2015.
 */
@Entity
public class SessionEntity extends BaseEntity {

    @OneToOne
    private UserEntity user;

    @Column(name = "SESSION_KEY")
    private String key;

    public SessionEntity(){
    }

    public SessionEntity(UserEntity user, String key) {
        this.user = user;
        this.key = key;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "user=" + user +
                ", key='" + key + '\'' +
                '}';
    }

    public static class Builder extends BaseEntity.Builder{

        protected Builder(){
            this.entity = new OwnedEntity();
        }

        private Builder buildFrom(SessionEntity ownedEntity) {
            super.buildFrom(ownedEntity);
            ((SessionEntity)entity).key = ownedEntity.key;
            ((SessionEntity)entity).user = ownedEntity.user;
            return this;
        }

        public SessionEntity.Builder key(String key){
            ((SessionEntity)entity).key = key;
            return this;
        }

        public SessionEntity.Builder user(UserEntity user){
            ((SessionEntity)entity).user = user;
            return this;

        }

        SessionEntity value(){
            return (SessionEntity)entity;
        }

    }


    public Builder builder() {
        return new Builder().buildFrom(new SessionEntity());
    }
}
