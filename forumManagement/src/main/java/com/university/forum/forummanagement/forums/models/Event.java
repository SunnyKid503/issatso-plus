package com.university.forum.forummanagement.forums.models;

import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.List;

@Entity
@DiscriminatorValue("EVENT")
public class Event extends PostBase{
    @OneToOne
    @JoinColumn(name = "banner_reference_id", nullable = false)
    private FileReference banner;
    private int capacity;
    private int attendees;
//    @Column(name = "start_date", nullable = false)
    @NotNull
    private Timestamp startDate;
//    @Column(name = "end_date", nullable = false)
    @NotNull
    private Timestamp endDate;
    private String longDescription;

    public Event() {
    }

    public Event(int id, int upVoteCount, int downVoteCount, int viewCount, String content, String title, Member author, List<PostCategory> categories, Timestamp createdAt, Timestamp updatedAt, List<TaggableObject> tags, List<Comment> comments, List<FileReference> files, FileReference banner, int capacity, int attendees, Timestamp startDate, Timestamp endDate, String longDescription) {
        super(id, upVoteCount, downVoteCount, viewCount, content, title, author, categories, createdAt, updatedAt, tags, comments, files);
        this.banner = banner;
        this.capacity = capacity;
        this.attendees = attendees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.longDescription = longDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public FileReference getBanner() {
        return banner;
    }

    public void setBanner(FileReference banner) {
        this.banner = banner;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }
}
