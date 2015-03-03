package com.github.collabeng.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.util.Date;

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

    public OwnedEntity(OwnedEntity ownedEntity) {
        super(ownedEntity);
        this.owner = ownedEntity.owner;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}
