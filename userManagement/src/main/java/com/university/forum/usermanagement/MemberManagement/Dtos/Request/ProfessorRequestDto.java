package com.university.forum.usermanagement.MemberManagement.Dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProfessorRequestDto {


    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    @Schema(description = "Email address of the professor", example = "professor@example.com")
    private String addressEmail;
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}",message ="Phone number must be exactly 8 digits" )
    private String phoneNumber;
    private String address;
    private String linkedInProfileUrl;
    private String profileImageUrl;

    @NotBlank
    @Size(min = 8, max = 8, message = "CIN must be exactly 8 digits")
    @Pattern(regexp = "\\d{8}", message = "CIN must contain only digits")
    private String cin;

    private Boolean sex;

    @NotNull
    private Set<Integer> rolesIds=new HashSet<>();

    @Past
    private LocalDate dob;


    @NotNull
    private Set<Integer> classGroupIds=new HashSet<>();
    private Integer departmentId;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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

    public @NotBlank(message = "Email address must not be blank") @Email(message = "Email should be valid") @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(@NotBlank(message = "Email address must not be blank") @Email(message = "Email should be valid") @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") String addressEmail) {
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

    public @NotBlank @Size(min = 8, max = 8, message = "CIN must be exactly 8 digits") @Pattern(regexp = "\\d{8}", message = "CIN must contain only digits") String getCin() {
        return cin;
    }

    public void setCin(@NotBlank @Size(min = 8, max = 8, message = "CIN must be exactly 8 digits") @Pattern(regexp = "\\d{8}", message = "CIN must contain only digits") String cin) {
        this.cin = cin;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public @NotNull Set<Integer> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(@NotNull Set<Integer> rolesIds) {
        this.rolesIds = rolesIds;
    }

    public @Past LocalDate getDob() {
        return dob;
    }

    public void setDob(@Past LocalDate dob) {
        this.dob = dob;
    }

    public @NotNull Set<Integer> getClassGroupIds() {
        return classGroupIds;
    }

    public void setClassGroupIds(@NotNull Set<Integer> classGroupIds) {
        this.classGroupIds = classGroupIds;
    }

    public ProfessorRequestDto() {
    }

    public ProfessorRequestDto(String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, Boolean sex, Set<Integer> rolesIds, LocalDate dob, Set<Integer> classGroupIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImageUrl = profileImageUrl;
        this.cin = cin;
        this.sex = sex;
        this.rolesIds = rolesIds;
        this.dob = dob;
        this.classGroupIds = classGroupIds;
    }
}
