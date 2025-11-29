package com.university.forum.usermanagement.ClassGroupManagement.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity

public class LevelOfStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String reference;


    @OneToMany(mappedBy = "levelOfStudy",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ClassGroup> classGroups=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

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

    public List<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public void setClassGroups(List<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public LevelOfStudy() {
    }

    public LevelOfStudy(int id, String name, String reference, List<ClassGroup> classGroups, Speciality speciality) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.classGroups = classGroups;
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "LevelOfStudy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
