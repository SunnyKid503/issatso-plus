package com.university.forum.forummanagement.structures.models;


import com.university.forum.forummanagement.membership.models.Professor;
import com.university.forum.forummanagement.membership.models.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLASS_GROUP")
public class ClassGroup extends TaggableObject {
    private String reference;

    @OneToMany(mappedBy = "classGroup",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    @ManyToMany(mappedBy = "classGroups", fetch = FetchType.LAZY)
    private List<Professor> professors ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelOfStudy_id")
    private LevelOfStudy levelOfStudy;

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
