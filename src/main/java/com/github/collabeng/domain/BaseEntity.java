package com.github.collabeng.domain;

import javax.persistence.*;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base entity, to generate the ID
 */
@MappedSuperclass
public abstract class BaseEntity implements Cloneable {

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

    public BaseEntity() {
    }

    public BaseEntity(BaseEntity baseEntity) {
        this.id = baseEntity.id;
        this.version = baseEntity.version;
        this.createdAt = baseEntity.createdAt;
        this.lastModifiedAt = baseEntity.lastModifiedAt;
    }

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

    public abstract static class Builder{
        protected BaseEntity entity;

        protected Builder(){
        }

        public Builder buildFrom(BaseEntity baseEntity) {
            checkNotNull(entity);
            entity.id = baseEntity.id;
            entity.version = baseEntity.version;
            entity.createdAt = baseEntity.createdAt;
            return this;
        }

        public Builder version(long version){
            entity.version = version;
            return this;
        }

        public Builder lastModifiedAt(Date lastModifiedAt){
            entity.lastModifiedAt = lastModifiedAt;
            return this;
        }
    }

    protected BaseEntity copy(){
        BaseEntity ret = null;
        try {
            ret = (BaseEntity) this.clone();

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public BaseEntity withId(Long id) {
        BaseEntity ret = copy();
        ret.id = id;
        return ret;
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


    @Override
    public String toString() {
        return "" + getClass().getName() +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                '}';
    }
}
