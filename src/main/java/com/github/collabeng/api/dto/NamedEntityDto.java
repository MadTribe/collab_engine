package com.github.collabeng.api.dto;

import com.github.collabeng.domain.NamedOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Version;
import java.util.Date;

/**
 * Created by paul.smout on 20/04/2015.
 */
public class NamedEntityDto {
    private long id;
    private long version;
    private Date createdAt;
    private Date lastModifiedAt;
    private String name;

    public NamedEntityDto() {
    }

    public NamedEntityDto(NamedOwnedEntity named) {
        this.id = named.getId();
        this.version = named.getVersion();
        this.createdAt = named.getCreatedAt();
        this.lastModifiedAt = named.getLastModifiedAt();
        this.name = named.getName();
    }

    public NamedEntityDto(long id, long version, Date createdAt, Date lastModifiedAt, String name) {
        this.id = id;
        this.version = version;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NamedEntityDto{" +
                "id=" + id +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", name='" + name + '\'' +
                '}';
    }
}
