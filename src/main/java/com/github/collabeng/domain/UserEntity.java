package com.github.collabeng.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by paul.smout on 26/01/2015.
 */
@Entity
public class UserEntity extends BaseEntity {
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ENCODING")
    private Encoding encoding;

    @Column(name = "SALT")
    private String salt;

    @Column(name = "ACTIVE")
    private boolean active = true;


    public UserEntity() {
    }

    public UserEntity(String username, String password, String salt, boolean active) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public boolean isActive() {
        return active;
    }

    public Encoding getEncoding() {
        return encoding;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", active=" + active +
                '}';
    }
}
