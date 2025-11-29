package com.university.forum.forummanagement.structures.models;


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("LEVEL_OF_STUDY")
public class LevelOfStudy extends TaggableObject {
    private String reference;

    @OneToMany(mappedBy = "levelOfStudy",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<ClassGroup> classGroups=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

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
