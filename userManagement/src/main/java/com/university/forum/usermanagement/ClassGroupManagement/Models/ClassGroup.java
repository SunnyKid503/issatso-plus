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

@Table(name = "class_group")
public class ClassGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String reference;

    @OneToMany(mappedBy = "classGroup",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    @ManyToMany(mappedBy = "classGroups")
    private List<Professor> professors ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelOfStudy_id")
    private LevelOfStudy levelOfStudy;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }

    public LevelOfStudy getLevelOfStudy() {
        return levelOfStudy;
    }

    public void setLevelOfStudy(LevelOfStudy levelOfStudy) {
        this.levelOfStudy = levelOfStudy;
    }

    public ClassGroup() {
    }

    public ClassGroup(int id, String name, String reference, List<Student> students, List<Professor> professors, LevelOfStudy levelOfStudy) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.students = students;
        this.professors = professors;
        this.levelOfStudy = levelOfStudy;
    }

    @Override
    public String toString() {
        return "ClassGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
