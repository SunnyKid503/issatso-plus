package com.university.forum.usermanagement.MemberManagement.Dtos.Response;

import com.university.forum.usermanagement.MemberManagement.Models.Enums.MemberType;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class MemberResponseDto {

    private UUID Id;
    private String firstName;
    private String lastName;
    private String addressEmail;

    private String phoneNumber;
    private String address;
    private String linkedInProfileUrl;
    private String profileImageUrl;
    private String cin;
    private LocalDate dob;
    private String studentNumber;
    private Integer classGroupId;
    private Boolean sex;;
    private Set<Integer> groupIds;
    private MemberType memberType;
    private String classGroupName;
    private String levelOfStudyName;
    private String specialityName;
    private String branchName;
    private String departmentName;

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    public String getLevelOfStudyName() {
        return levelOfStudyName;
    }

    public void setLevelOfStudyName(String levelOfStudyName) {
        this.levelOfStudyName = levelOfStudyName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Integer classGroupId) {
        this.classGroupId = classGroupId;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Set<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<Integer> groupIds) {
        this.groupIds = groupIds;
    }


    public MemberResponseDto() {
    }


    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public MemberResponseDto(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, LocalDate dob, String studentNumber, Integer classGroupId, Boolean sex, Set<Integer> groupIds, MemberType memberType, String classGroupName, String levelOfStudyName, String specialityName, String branchName, String departmentName) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImageUrl = profileImageUrl;
        this.cin = cin;
        this.dob = dob;
        this.studentNumber = studentNumber;
        this.classGroupId = classGroupId;
        this.sex = sex;
        this.groupIds = groupIds;
        this.memberType = memberType;
        this.classGroupName = classGroupName;
        this.levelOfStudyName = levelOfStudyName;
        this.specialityName = specialityName;
        this.branchName = branchName;
        this.departmentName = departmentName;
    }
}
