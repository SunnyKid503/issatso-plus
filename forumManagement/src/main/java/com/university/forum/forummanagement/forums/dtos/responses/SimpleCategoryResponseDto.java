package com.university.forum.forummanagement.forums.dtos.responses;

import com.university.forum.forummanagement.forums.models.PostCategory;

public class SimpleCategoryResponseDto {
    private int id;
    private String typename;

    public SimpleCategoryResponseDto() {
    }

    public SimpleCategoryResponseDto(int id, String typename) {
        this.id = id;
        this.typename = typename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public static SimpleCategoryResponseDto toSimpleCategoryResponseDto(PostCategory category)
    {
        return new SimpleCategoryResponseDto(category.getId(), category.getTypename());
    }
}
