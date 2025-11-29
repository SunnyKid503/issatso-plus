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
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends Personnel {
    public Administrator() {
    }

    public Administrator(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp createdAt, Timestamp updatedAt, MemberType memberType, Set<Role> roles) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, sex, dob, createdAt, updatedAt, memberType, roles);
    }

}
