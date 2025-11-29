package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.models.Event;
import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.forums.models.PostCategory;
import com.university.forum.forummanagement.forums.models.PostType;
import com.university.forum.forummanagement.membership.dtos.responses.ForumMemberResponseDto;

import java.sql.Timestamp;
import java.util.List;

public class EventResponseDto extends PostResponseDto{
    private int capacity;
    private int attendees;
    private String banner;
    private Timestamp startDate;
    private Timestamp endDate;
    private String longDescription;

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

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public EventResponseDto(int id, int upVoteCount, int downVoteCount, int viewCount, String content, String title, ForumMemberResponseDto author, List<PostCategory> categories, Timestamp createdAt, Timestamp updatedAt, List<TaggableObjectResponseDto> tags, List<String> files, int capacity, int attendees, String banner, Timestamp startDate, Timestamp endDate, String longDescription) {
        super(id, upVoteCount, downVoteCount, viewCount, content, title, author, categories, createdAt, updatedAt, null, tags, files);
        this.capacity = capacity;
        this.attendees = attendees;
        this.banner = banner;
        this.startDate = startDate;
        this.endDate = endDate;
        this.longDescription = longDescription;
    }

    public EventResponseDto()
    {

    }

    public static EventResponseDto toEventResponseDto(Event event)
    {
        return new EventResponseDto(
                event.getId(),
                event.getUpVoteCount(),
                event.getDownVoteCount(),
                event.getViewCount(),
                event.getContent(),
                event.getTitle(),
                ForumMemberResponseDto.toMemberResponseDto(event.getAuthor()),
                event.getCategories(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getTags().stream().map(TaggableObjectResponseDto::create).toList(),
                event.getFiles().stream().map(FileReference::getImageUrl).toList(),
                event.getCapacity(),
                event.getAttendees(),
                event.getBanner().getImageUrl(),
                event.getStartDate(),
                event.getEndDate(),
                event.getLongDescription()
        );
    }


}
