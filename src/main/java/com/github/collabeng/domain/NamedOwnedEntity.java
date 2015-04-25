package com.github.collabeng.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by paul.smout on 15/04/2015.
 * Entity where the name and owner form a unique primarykey
 */

@MappedSuperclass
public class NamedOwnedEntity extends OwnedEntity {
    @Column(name = "NAME")
    protected String name;

    public NamedOwnedEntity() {
    }

    public NamedOwnedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
