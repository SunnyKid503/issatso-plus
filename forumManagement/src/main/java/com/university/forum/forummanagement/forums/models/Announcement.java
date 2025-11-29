package com.university.forum.forummanagement.forums.models;

import com.university.forum.forummanagement.membership.models.asbtracts.Personnel;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    @ManyToOne
    private Personnel author;
    private String longDescription;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<TaggableObject> tags;
    @CreationTimestamp
    private Timestamp CreatedAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
    @OneToMany(fetch = FetchType.LAZY)
    private List<FileReference> files = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Personnel getAuthor() {
        return author;
    }

    public void setAuthor(Personnel author) {
        this.author = author;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<TaggableObject> getTags() {
        return tags;
    }

    public void setTags(List<TaggableObject> tags) {
        this.tags = tags;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<FileReference> getFiles() {
        return files;
    }

    public void setFiles(List<FileReference> files) {
        this.files = files;
    }
}
