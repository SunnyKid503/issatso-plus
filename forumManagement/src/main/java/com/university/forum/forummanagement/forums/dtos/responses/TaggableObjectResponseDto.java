package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.structures.models.TaggableObject;

public class TaggableObjectResponseDto {
    private int id;
    private String name;

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

    public TaggableObjectResponseDto() {
    }

    public TaggableObjectResponseDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TaggableObjectResponseDto create(TaggableObject tag)
    {
        return new TaggableObjectResponseDto(tag.getId(), tag.getName());
    }
}
