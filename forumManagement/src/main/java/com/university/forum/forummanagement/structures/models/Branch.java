package com.university.forum.forummanagement.structures.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("BRANCH")
public class Branch extends TaggableObject {
    private String reference;
    @OneToMany(mappedBy = "branch",orphanRemoval = true,cascade =CascadeType.ALL )
    @Fetch(FetchMode.SELECT)
    private List<Speciality> specialities =new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "department_id")
    private Department department;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Branch() {
    }

    public Branch(int id, String name, String reference, List<Speciality> specialities, Department department) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.specialities = specialities;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }

}
