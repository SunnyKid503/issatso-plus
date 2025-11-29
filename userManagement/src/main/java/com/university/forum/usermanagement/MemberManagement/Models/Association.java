package com.university.forum.usermanagement.MemberManagement.Models;

import com.university.forum.usermanagement.MemberManagement.Models.Abstract_Classes.Personnel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ASSOCIATION")
public class Association extends Personnel {
    private String associationName;

    public Association(String associationName) {
        this.associationName = associationName;
    }
    public Association() {}

    public String getAssociationName() {
        return associationName;
    }

    public void setAssociationName(String associationName) {
        this.associationName = associationName;
    }
}
