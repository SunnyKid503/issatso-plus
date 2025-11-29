package com.university.forum.usermanagement.MemberManagement.Dtos.Response;

import com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response.DepartmentResponseDto;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Branch;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.ClassGroupManagement.Models.LevelOfStudy;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Speciality;
import com.university.forum.usermanagement.MemberManagement.Models.Student;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public class StudentResponseDto {

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
    private Boolean sex;
    private DepartmentResponseDto department;
    private DepartmentResponseDto branch;
    private DepartmentResponseDto specialty;
    private DepartmentResponseDto levelOfStudy;
    private DepartmentResponseDto group;

    public DepartmentResponseDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentResponseDto department) {
        this.department = department;
    }

    public DepartmentResponseDto getBranch() {
        return branch;
    }

    public void setBranch(DepartmentResponseDto branch) {
        this.branch = branch;
    }

    public DepartmentResponseDto getSpecialty() {
        return specialty;
    }

    public void setSpecialty(DepartmentResponseDto specialty) {
        this.specialty = specialty;
    }



    public DepartmentResponseDto getGroup() {
        return group;
    }

    public void setGroup(DepartmentResponseDto group) {
        this.group = group;
    }

    public UUID getId() {
        return Id;
    }


    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
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


    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public DepartmentResponseDto getLevelOfStudy() {
        return levelOfStudy;
    }

    public void setLevelOfStudy(DepartmentResponseDto levelOfStudy) {
        this.levelOfStudy = levelOfStudy;
    }

    public StudentResponseDto() {
    }

    public StudentResponseDto(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, LocalDate dob, String studentNumber, Boolean sex, DepartmentResponseDto department, DepartmentResponseDto branch, DepartmentResponseDto specialty, DepartmentResponseDto levelOfStudy, DepartmentResponseDto group) {
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
        this.sex = sex;
        this.department = department;
        this.branch = branch;
        this.specialty = specialty;
        this.levelOfStudy = levelOfStudy;
        this.group = group;
    }


    public static StudentResponseDto convertToStudentResponseDto(Student student) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setFirstName(student.getFirstName());
        responseDto.setLastName(student.getLastName());
        responseDto.setAddressEmail(student.getAddressEmail());
        responseDto.setPhoneNumber(student.getPhoneNumber());
        responseDto.setAddress(student.getAddress());
        responseDto.setLinkedInProfileUrl(student.getLinkedInProfileUrl());
        responseDto.setCin(student.getCin());
        responseDto.setDob(student.getDob());
        responseDto.setStudentNumber(student.getStudentNumber());
        responseDto.setSex(student.getSex());
        responseDto.setProfileImageUrl(student.getProfileImageUrl());

        return responseDto;
    }

    public static StudentResponseDto convertToStudentResponseDtoFull(Student student, DepartmentResponseDto department, DepartmentResponseDto branch, DepartmentResponseDto speciality, DepartmentResponseDto levelOfStudy, DepartmentResponseDto group) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setFirstName(student.getFirstName());
        responseDto.setLastName(student.getLastName());
        responseDto.setAddressEmail(student.getAddressEmail());
        responseDto.setPhoneNumber(student.getPhoneNumber());
        responseDto.setAddress(student.getAddress());
        responseDto.setLinkedInProfileUrl(student.getLinkedInProfileUrl());
        responseDto.setCin(student.getCin());
        responseDto.setDob(student.getDob());
        responseDto.setStudentNumber(student.getStudentNumber());
        responseDto.setSex(student.getSex());
        responseDto.setProfileImageUrl(student.getProfileImageUrl());
        responseDto.setDepartment(department);
        responseDto.setBranch(branch);
        responseDto.setSpecialty(speciality);
        responseDto.setLevelOfStudy(levelOfStudy);
        responseDto.setGroup(group);
        return responseDto;
    }

}
