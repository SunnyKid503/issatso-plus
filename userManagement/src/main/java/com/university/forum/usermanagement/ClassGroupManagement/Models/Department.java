package com.university.forum.usermanagement.ClassGroupManagement.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.university.forum.usermanagement.MemberManagement.Models.Professor;
import com.university.forum.usermanagement.MemberManagement.Models.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String reference;

    @OneToMany(mappedBy = "department",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Branch> branches=new ArrayList<>();

    @OneToMany(mappedBy = "belongedDepartment",cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<Professor> professors = new ArrayList<>();

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public Department() {}
    public Department(String name, String reference) {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
