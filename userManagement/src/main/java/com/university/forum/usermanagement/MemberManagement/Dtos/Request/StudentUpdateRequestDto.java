package com.university.forum.usermanagement.MemberManagement.Dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StudentUpdateRequestDto {

    private String firstName;
    private String lastName;
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    @Email
    private String addressEmail;
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}",message ="Phone number must be exactly 8 digits" )
    private String phoneNumber;

    private String address;

    private String linkedInProfileUrl;

    private String profileImageUrl;

    private Boolean sex;
    @Past
    private LocalDate dob;

    @Min(value = 1, message = "Class group ID must be greater than 0")
    private Integer classGroupId;

    public StudentUpdateRequestDto(String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, Boolean sex, LocalDate dob, Integer classGroupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImageUrl = profileImageUrl;
        this.sex = sex;
        this.dob = dob;
        this.classGroupId = classGroupId;
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

    public @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") @Email String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(@Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") @Email String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public @Size(min = 8, max = 8) @Pattern(regexp = "\\d{8}", message = "Phone number must be exactly 8 digits") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 8, max = 8) @Pattern(regexp = "\\d{8}", message = "Phone number must be exactly 8 digits") String phoneNumber) {
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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public @Past LocalDate getDob() {
        return dob;
    }

    public void setDob(@Past LocalDate dob) {
        this.dob = dob;
    }

    public @Min(value = 1, message = "Class group ID must be greater than 0") Integer getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(@Min(value = 1, message = "Class group ID must be greater than 0") Integer classGroupId) {
        this.classGroupId = classGroupId;
    }

    public StudentUpdateRequestDto() {
    }
}
