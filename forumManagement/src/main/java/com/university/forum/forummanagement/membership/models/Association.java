package com.university.forum.forummanagement.membership.models;

import com.university.forum.forummanagement.membership.models.asbtracts.Personnel;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@DiscriminatorValue("ASSOCIATION")
public class Association extends Personnel {
    private String associationName;

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }

    public Association() {
    }

    public Association(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp createdAt, Timestamp updatedAt, MemberType memberType, Set<Role> roles, String associationName) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, sex, dob, createdAt, updatedAt, memberType, roles);
        this.associationName = associationName;
    }
}
