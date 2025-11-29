package com.university.forum.forummanagement.structures.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALITY")
public class Speciality extends TaggableObject {
    private String reference;

    @OneToMany(mappedBy = "speciality",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LevelOfStudy> levelsOfStudy=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public Speciality(int id, String name, String reference, List<LevelOfStudy> levelsOfStudy, Branch branch) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.levelsOfStudy = levelsOfStudy;
        this.branch = branch;
    }
    public Speciality() {}
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<LevelOfStudy> getLevelsOfStudy() {
        return levelsOfStudy;
    }

    public void setLevelsOfStudy(List<LevelOfStudy> levelsOfStudy) {
        this.levelsOfStudy = levelsOfStudy;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                ", levelsOfStudy=" + levelsOfStudy +
                '}';
    }
}
