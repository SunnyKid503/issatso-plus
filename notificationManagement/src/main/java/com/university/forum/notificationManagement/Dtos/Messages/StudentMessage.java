package com.university.forum.notificationManagement.Dtos.Messages;

import com.university.forum.notificationManagement.Models.Enums.MemberType;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StudentMessage extends MemberMessage{

    private String studentNumber;
    private Integer classGroupId;
    private String classGroupName;

    public StudentMessage() {
    }
    @Override
    public String toString() {
        return "StudentMessage{" +
                "studentNumber='" + studentNumber + '\'' +
                ", classGroupId=" + classGroupId +
                ", classGroupName='" + classGroupName + '\'' +
                '}';
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Integer classGroupId) {
        this.classGroupId = classGroupId;
    }

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    public StudentMessage(UUID id, String firstName, String lastName, String addressEmail, String phoneNumber, String address, String linkedInProfileUrl, String profileImageUrl, String cin, LocalDate dob, Set<Integer> roleIds, Boolean sex, MemberType memberType, String studentNumber, Integer classGroupId, String classGroupName) {
        super(id, firstName, lastName, addressEmail, phoneNumber, address, linkedInProfileUrl, profileImageUrl, cin, dob, roleIds, sex, memberType);
        this.studentNumber = studentNumber;
        this.classGroupId = classGroupId;
        this.classGroupName = classGroupName;
    }

    public StudentMessage(String studentNumber, Integer classGroupId, String classGroupName) {
        this.studentNumber = studentNumber;
        this.classGroupId = classGroupId;
        this.classGroupName = classGroupName;
    }
}
