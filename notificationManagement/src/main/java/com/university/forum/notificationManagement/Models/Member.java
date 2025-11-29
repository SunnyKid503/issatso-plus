package com.university.forum.notificationManagement.Models;

import com.university.forum.notificationManagement.Models.Enums.MemberType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Member {
    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;
    @Column(nullable = false,unique = true)
    private String addressEmail;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @Size(min = 8, max = 8)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberType memberType;

    private String classGroupName;
    private String levelOfStudyName;
    private String specialityName  ;
    private String branchName;
    private String departmentName;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FcmToken> fcmTokens;


    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Member(UUID id, String addressEmail, String name, String lastName, String phoneNumber, MemberType memberType, String classGroupName, String levelOfStudyName, String specialityName, String branchName, String departmentName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.addressEmail = addressEmail;
        this.firstName = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.memberType = memberType;
        this.classGroupName = classGroupName;
        this.levelOfStudyName = levelOfStudyName;
        this.specialityName = specialityName;
        this.branchName = branchName;
        this.departmentName = departmentName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddressEmail() {
        return addressEmail;
    }

    public void setAddressEmail(String addressEmail) {
        this.addressEmail = addressEmail;
    }

    public String getName() {
        return firstName;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @Size(min = 8, max = 8) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 8, max = 8) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Member() {
    }

    @Override
    public String toString() {
        return "Member{" +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", departmentName='" + departmentName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", specialityName='" + specialityName + '\'' +
                ", levelOfStudyName='" + levelOfStudyName + '\'' +
                ", classGroupName='" + classGroupName + '\'' +
                ", memberType=" + memberType +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + firstName + '\'' +
                ", addressEmail='" + addressEmail + '\'' +
                ", id=" + id +
                '}';
    }
}
