package com.university.forum.usermanagement.MemberManagement.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.MemberManagement.Models.Enums.MemberType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("STUDENT")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "studentNumber"))
public class Student extends Member {
    @Column(nullable = false, unique = true)
    private String studentNumber;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private ClassGroup classGroup;
    public Student() {}

    public Student(String studentNumber, ClassGroup classGroup) {
        this.studentNumber = studentNumber;
        this.classGroup = classGroup;
    }


//    public Student(UUID id, String lastName, String addressEmail, String phoneNumber, String address, String password, String linkedInProfileUrl, String profileImagePublic_Id, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp createdAt, Timestamp updatedAt, Set<Role> roles, MemberType memberType, String firstName, String studentNumber, ClassGroup classGroup) {
//        super(id, lastName, addressEmail, phoneNumber, address, password, linkedInProfileUrl, profileImagePublic_Id, profileImageUrl, cin, sex, dob, createdAt, updatedAt, roles, memberType, firstName);
//        this.studentNumber = studentNumber;
//        this.classGroup = classGroup;
//    }
//
//    public Student(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String password, String linkedInProfileUrl, String profileImagePublic_Id, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp last_login_attempt, Timestamp createdAt, Timestamp updatedAt, Set<Role> roles, MemberType memberType, String studentNumber, ClassGroup classGroup) {
//        super(id, firstName, lastName, addressEmail, phoneNumber, address, password, linkedInProfileUrl, profileImagePublic_Id, profileImageUrl, cin, sex, dob, last_login_attempt, createdAt, updatedAt, roles, memberType);
//        this.studentNumber = studentNumber;
//        this.classGroup = classGroup;
//    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }
}
