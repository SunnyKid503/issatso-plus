package com.university.forum.usermanagement.MemberManagement.Dtos.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProfessorUpdateRequestDto {


    private String firstName;

    private String lastName;

    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    @Schema(description = "Email address of the professor", example = "professor@example.com")
    private String addressEmail;

    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}",message ="Phone number must be exactly 8 digits" )
    private String phoneNumber;

    private String address;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Size(min = 8, message = "Confirm Password must be at least 8 characters long")
    private String confirmPassword;
    private String linkedInProfileUrl;

    private String profileImageUrl;

    private Boolean sex;

    @Past
    private LocalDate dob;


    private Set<Integer> classGroupIds=new HashSet<>();
    private Integer departmentId;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmnetId) {
        this.departmentId = departmnetId;
    }

    public ProfessorUpdateRequestDto() {
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

    public @Email(message = "Email should be valid") @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(@Email(message = "Email should be valid") @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") String addressEmail) {
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

    public @Size(min = 8, message = "Password must be at least 8 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8, message = "Password must be at least 8 characters long") String password) {
        this.password = password;
    }

    public @Size(min = 8, message = "Confirm Password must be at least 8 characters long") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@Size(min = 8, message = "Confirm Password must be at least 8 characters long") String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public Set<Integer> getClassGroupIds() {
        return classGroupIds;
    }

    public void setClassGroupIds(Set<Integer> classGroupIds) {
        this.classGroupIds = classGroupIds;
    }

    public ProfessorUpdateRequestDto(String firstName, String lastName, String addressEmail, String phoneNumber, String address, String password, String confirmPassword, String linkedInProfileUrl, String profileImageUrl, Boolean sex, LocalDate dob, Set<Integer> classGroupIds,Integer departmentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImageUrl = profileImageUrl;
        this.sex = sex;
        this.dob = dob;
        this.classGroupIds = classGroupIds;
        this.departmentId = departmentId;
    }
}
