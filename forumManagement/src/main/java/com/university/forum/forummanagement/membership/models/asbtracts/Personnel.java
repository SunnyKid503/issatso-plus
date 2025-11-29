package com.university.forum.forummanagement.membership.models.asbtracts;

import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.models.Role;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "personnel_type")
public abstract class Personnel extends Member {
    public Personnel() {
    }

    public Personnel(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp createdAt, Timestamp updatedAt, MemberType memberType, Set<Role> roles) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, sex, dob, createdAt, updatedAt, memberType, roles);
    }

}
