package com.github.collabeng.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * Created by paul.smout on 27/02/2015.
 */
@MappedSuperclass
public class OwnedEntity extends BaseEntity {
    @OneToOne
    private UserEntity owner;

    public OwnedEntity() {
    }

    public OwnedEntity(UserEntity owner) {
        this.owner = owner;
    }

    public UserEntity getOwner() {
        return owner;
    }
}
