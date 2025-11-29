package com.university.forum.forummanagement.structures.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("DEPARTMENT")
public class Department extends TaggableObject {
    private String reference;

    @OneToMany(mappedBy = "department",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Branch> branches=new ArrayList<>();

    public Department() {}

    public Department(String name, String reference) {
        this.name = name;
        this.reference = reference;
    }

    public Department(String name, String reference, List<Branch> branches) {
        this.name = name;
        this.reference = reference;
        this.branches = branches;
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
