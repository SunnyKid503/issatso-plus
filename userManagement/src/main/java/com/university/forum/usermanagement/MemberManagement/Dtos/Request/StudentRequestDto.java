package com.university.forum.usermanagement.MemberManagement.Dtos.Request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentRequestDto {
    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    @Schema(description = "Email address of the student", example = "student@example.com")
    private String addressEmail;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}",message ="Phone number must be exactly 8 digits" )
    private String phoneNumber;

    private String address;

    private String linkedInProfileUrl;

    private String profileImageUrl;

    @NotBlank
    @Min(value = 8)
    @Pattern(regexp = "\\d{8}",message ="Student number must be exactly 8 digits" )
    private String studentNumber;
    @NotBlank
    @Size(min = 8, max = 8, message = "CIN must be exactly 8 digits")
    @Pattern(regexp = "\\d{8}", message = "CIN must contain only digits")
    private String cin;

    private Boolean sex;

    @NotNull
    private Set<Integer> rolesIds=new HashSet<>();

    @Past
    private LocalDate dob;


    @Min(value = 1, message = "Class group ID must be greater than 0")
    private Integer classGroupId;



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

    public StudentRequestDto() {
    }

    public @NotBlank(message = "First name must not be blank") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name must not be blank") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name must not be blank") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name must not be blank") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Email address must not be blank") @Email(message = "Email should be valid") String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(@NotBlank(message = "Email address must not be blank") @Email(message = "Email should be valid") String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public @Size(min = 8, max = 8) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 8, max = 8) String phoneNumber) {
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

    public @NotBlank @Size(min = 8, max = 8, message = "CIN must be exactly 8 digits") @Pattern(regexp = "\\d{8}", message = "CIN must contain only digits") String getCin() {
        return cin;
    }

    public void setCin(@NotBlank @Size(min = 8, max = 8, message = "CIN must be exactly 8 digits") @Pattern(regexp = "\\d{8}", message = "CIN must contain only digits") String cin) {
        this.cin = cin;
    }

    public @Past LocalDate getDob() {
        return dob;
    }

    public void setDob(@Past LocalDate dob) {
        this.dob = dob;
    }

    public @NotBlank String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(@NotBlank String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public @Min(value = 1, message = "Class group ID must be greater than 0") Integer getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(@Min(value = 1, message = "Class group ID must be greater than 0") Integer classGroupId) {
        this.classGroupId = classGroupId;
    }

    public @NotNull Set<Integer> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(@NotNull Set<Integer> rolesIds) {
        this.rolesIds = rolesIds;
    }

    public StudentRequestDto(String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String studentNumber, String cin, Boolean sex, Set<Integer> rolesIds, LocalDate dob, Integer classGroupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImageUrl = profileImageUrl;
        this.studentNumber = studentNumber;
        this.cin = cin;
        this.sex = sex;
        this.rolesIds = rolesIds;
        this.dob = dob;
        this.classGroupId = classGroupId;
    }
}
