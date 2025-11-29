package com.university.forum.notificationManagement.Dtos.Messages;

import com.university.forum.notificationManagement.Models.Enums.MemberType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProfessorMessage extends MemberMessage {

    private Set<Integer> classGroupIds=new HashSet<>();

    public ProfessorMessage(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, LocalDate dob, Set<Integer> roleIds, Boolean sex, MemberType memberType, Set<Integer> classGroupIds) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, dob, roleIds, sex, memberType);
        this.classGroupIds = classGroupIds;
    }

    public ProfessorMessage() {
    }
    public ProfessorMessage(Set<Integer> classGroupIds) {
        this.classGroupIds = classGroupIds;
    }

    public Set<Integer> getClassGroupIds() {
        return classGroupIds;
    }

    public void setClassGroupIds(Set<Integer> classGroupIds) {
        this.classGroupIds = classGroupIds;
    }

}