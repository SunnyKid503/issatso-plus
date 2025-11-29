package com.university.forum.usermanagement.ClassGroupManagement.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String reference;

    @OneToMany(mappedBy = "speciality",cascade = CascadeType.ALL,orphanRemoval = true)
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
