package com.university.forum.usermanagement.MemberManagement.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.university.forum.usermanagement.ClassGroupManagement.Models.ClassGroup;
import com.university.forum.usermanagement.ClassGroupManagement.Models.Department;
import com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes.Personnel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Personnel {


    @ManyToMany
    @JoinTable(
            name = "professor_group",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "class_group_id")
    )
    private Set<ClassGroup> classGroups=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department belongedDepartment;


    public Department getBelongedDepartment() {
        return belongedDepartment;
    }

    public void setBelongedDepartment(Department belongedDepartment) {
        this.belongedDepartment = belongedDepartment;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public void setClassGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }

    public Professor() {
    }

    public Professor(Set<ClassGroup> classGroups, Department belongedDepartment) {
        this.classGroups = classGroups;
        this.belongedDepartment = belongedDepartment;
    }
}
