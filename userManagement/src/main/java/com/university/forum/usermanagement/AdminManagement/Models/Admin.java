package com.university.forum.usermanagement.AdminManagement.Models;

import com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes.BaseUser;
import com.university.forum.usermanagement.MemberManagement.Models.Role;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TimeZoneColumn;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends BaseUser {

    @Column(unique = true,nullable = false)
    private String username;

    private String fullName;


    public Admin() {}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Admin(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }
}