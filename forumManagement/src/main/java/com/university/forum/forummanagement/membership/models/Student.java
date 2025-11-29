package com.university.forum.forummanagement.membership.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.university.forum.forummanagement.membership.models.enums.MemberType;
import com.university.forum.forummanagement.structures.models.ClassGroup;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Member {
    private String studentNumber;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference
    private ClassGroup classGroup;
    public Student() {}

    public Student(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, Boolean sex, LocalDate dob, Timestamp createdAt, Timestamp updatedAt, MemberType memberType, Set<Role> roles, String studentNumber, ClassGroup classGroup) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, sex, dob, createdAt, updatedAt, memberType, roles);
        this.studentNumber = studentNumber;
        this.classGroup = classGroup;
    }

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
