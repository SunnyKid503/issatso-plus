package com.university.forum.forummanagement.membership.dtos.responses;


import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.models.enums.MemberType;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class ForumMemberResponseDto {

    private UUID Id;
    private String firstName;
    private String lastName;
    private String addressEmail;
    private String phoneNumber;
    private String linkedInProfileUrl;
    private String profileImageUrl;
    private String cin;
    private LocalDate dob;
    private Boolean sex;
    private MemberType memberType;

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLinkedInProfileUrl() {
        return linkedInProfileUrl;
    }

    public void setLinkedInProfileUrl(String linkedInProfileUrl) {
        this.linkedInProfileUrl = linkedInProfileUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public ForumMemberResponseDto(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String linkedInProfileUrl, String profileImageUrl, String cin, LocalDate dob, Boolean sex, MemberType memberType) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImageUrl = profileImageUrl;
        this.cin = cin;
        this.dob = dob;
        this.sex = sex;
        this.memberType = memberType;
    }

    public ForumMemberResponseDto() {
    }


    public static ForumMemberResponseDto toMemberResponseDto(Member member)
    {
        return new ForumMemberResponseDto(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getAddressEmail(),
                member.getPhoneNumber(),
                member.getLinkedInProfileUrl(),
                member.getProfileImageUrl(),
                member.getCin(),
                member.getDob(),
                member.getSex(),
                member.getMemberType()
        );
    }
}
