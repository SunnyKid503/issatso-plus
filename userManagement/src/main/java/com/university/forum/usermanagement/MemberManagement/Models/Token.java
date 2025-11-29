package com.university.forum.usermanagement.MemberManagement.Models;

import com.university.forum.usermanagement.AdminManagement.Models.Admin;
import com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes.BaseUser;
import jakarta.persistence.*;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true,columnDefinition = "TEXT")
    private String token;

    private boolean revoked;
    private boolean expired;

    @ManyToOne
    private BaseUser user;

    public Token(String token, boolean revoked, boolean expired, BaseUser user) {
        this.token = token;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }

    public Token() {

    }

    public BaseUser getUser() {
        return user;
    }

    public void setUser(BaseUser user) {
        this.user = user;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

}
