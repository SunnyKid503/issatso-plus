package com.university.forum.usermanagement.MemberManagement.Models;


import com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes.BaseUser;
import com.university.forum.usermanagement.MemberManagement.Models.Enums.MemberType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "member_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("MEMBER")
public class Member  extends BaseUser {

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String addressEmail;
    @Size(min = 8, max = 8)
    private String phoneNumber;
    private String address;

    private String linkedInProfileUrl;

    private String profileImagePublic_Id;
    private String profileImageUrl;
    @Column(unique = true, nullable = false)
    @Size(min = 8, max = 8)
    private String cin;
    @Column(nullable = false)
    private Boolean sex = true;
    private LocalDate dob;

    @OneToMany(mappedBy = "bannedUser")
    private List<Ban> bans = new ArrayList<>();




    public List<Ban> getBans() {
        return bans;
    }

    public void setBans(List<Ban> bans) {
        this.bans = bans;
    }

    public Member( String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImagePublic_Id, String profileImageUrl, String cin, Boolean sex, LocalDate dob, String firstName) {

        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImagePublic_Id = profileImagePublic_Id;
        this.profileImageUrl = profileImageUrl;
        this.cin = cin;
        this.sex = sex;
        this.dob = dob;
        this.firstName = firstName;
    }

    public Member(String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImagePublic_Id, String profileImageUrl, String cin, Boolean sex, LocalDate dob) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmail = addressEmail;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.linkedInProfileUrl = linkedInProfileUrl;
        this.profileImagePublic_Id = profileImagePublic_Id;
        this.profileImageUrl = profileImageUrl;
        this.cin = cin;
        this.sex = sex;
        this.dob = dob;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public @Size(min = 8, max = 8) String getCin() {
        return cin;
    }

    public void setCin(@Size(min = 8, max = 8) String cin) {
        this.cin = cin;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public Member() {
    }

    public String getProfileImagePublic_Id() {
        return profileImagePublic_Id;
    }

    public void setProfileImagePublic_Id(String profileImagePublic_Id) {
        this.profileImagePublic_Id = profileImagePublic_Id;
    }


}
