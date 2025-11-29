package com.university.forum.forummanagement.membership.models;

import com.university.forum.forummanagement.membership.models.asbtracts.Personnel;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import com.university.forum.forummanagement.structures.models.ClassGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Personnel {


    @ManyToMany
    @JoinTable(
            name = "professor_group",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "class_group_id")
    )
    private Set<ClassGroup> classGroups=new HashSet<>();

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public void setClassGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }

    public Professor() {
    }

    public Professor(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp createdAt, Timestamp updatedAt, MemberType memberType, Set<Role> roles, Set<ClassGroup> classGroups) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, sex, dob, createdAt, updatedAt, memberType, roles);
        this.classGroups = classGroups;
    }
}
