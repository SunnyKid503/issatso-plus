package com.university.forum.usermanagement.MemberManagement.Dtos.Response;

import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProfessorResponseDto {

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
    private Set<DepartmentResponseDto> classGroups=new HashSet<>();
    private Boolean sex;
    private DepartmentResponseDto belongedDepartment;

    public DepartmentResponseDto getBelongedDepartment() {
        return belongedDepartment;
    }

    public void setBelongedDepartment(DepartmentResponseDto belongedDepartment) {
        this.belongedDepartment = belongedDepartment;
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



    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public ProfessorResponseDto() {
    }

    public ProfessorResponseDto(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, LocalDate dob, Set<DepartmentResponseDto> classGroupId, Boolean sex, DepartmentResponseDto belongedDepartment) {
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
        this.classGroups = classGroupId;
        this.sex = sex;
        this.belongedDepartment = belongedDepartment;
    }

    public Set<DepartmentResponseDto> getClassGroups() {
        return classGroups;
    }

    public void setClassGroups(Set<DepartmentResponseDto> classGroups) {
        this.classGroups = classGroups;
    }
}
