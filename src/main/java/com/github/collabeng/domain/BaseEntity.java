package com.github.collabeng.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Base entity, to generate the ID
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Version
    @Column(name = "VERSION")
    private long version;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "LAST_MODIFIED_AT")
    private Date lastModifiedAt;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        lastModifiedAt = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        version++;
        lastModifiedAt = new Date();
    }

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }
}
