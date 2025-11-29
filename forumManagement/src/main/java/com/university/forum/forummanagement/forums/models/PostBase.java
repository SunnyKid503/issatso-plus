package com.university.forum.forummanagement.forums.models;

import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "base_type", discriminatorType = DiscriminatorType.STRING)
public class PostBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    protected int upVoteCount;
    protected int downVoteCount;
    protected  int viewCount;
    @NotNull
    @NotBlank
    private String content;
    @NotNull
    @NotBlank
    private String title;
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<PostCategory> categories = new ArrayList<>();
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
//    @Column(name = "base_type")
//    private PostBaseType baseType;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<TaggableObject> tags = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    private List<FileReference> files = new ArrayList<>();
    private boolean isArchived;


    public PostBase(int id, int upVoteCount, int downVoteCount, int viewCount, String content, String title, Member author, List<PostCategory> categories, Timestamp createdAt, Timestamp updatedAt, List<TaggableObject> tags, List<Comment> comments, List<FileReference> files) {
        this.id = id;
        this.upVoteCount = upVoteCount;
        this.downVoteCount = downVoteCount;
        this.viewCount = viewCount;
        this.content = content;
        this.title = title;
        this.author = author;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tags = tags;
        this.comments = comments;
        this.files = files;
    }

    public PostBase() {
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(int upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public int getDownVoteCount() {
        return downVoteCount;
    }

    public void setDownVoteCount(int downVoteCount) {
        this.downVoteCount = downVoteCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Member getAuthor() {
        return author;
    }

    public void setAuthor(Member author) {
        this.author = author;
    }

    public List<PostCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PostCategory> categories) {
        this.categories = categories;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TaggableObject> getTags() {
        return tags;
    }

    public void setTags(List<TaggableObject> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<FileReference> getFiles() {
        return files;
    }

    public void setFiles(List<FileReference> files) {
        this.files = files;
    }
}
