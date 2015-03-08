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


    public UserEntity getOwner() {
        return owner;
    }


    public OwnedEntity withOwner(UserEntity owner){
        OwnedEntity ret = (OwnedEntity)copy();
        ret.owner = owner;
        return ret;
    }

    @Override
    public String toString() {
        return "" + super.toString() +
                "owner=" + owner +
                "}";
    }
}
