package com.university.forum.forummanagement.forums.dtos.responses;

public class CategoryResponseDto{
    private int id;
    private String typename;
    private int newPostCount;
    private int totalPostCount;

    public CategoryResponseDto() {
    }

    public CategoryResponseDto(int id, String typename, int newPostCount, int totalPostCount) {
        this.id = id;
        this.typename = typename;
        this.newPostCount = newPostCount;
        this.totalPostCount = totalPostCount;
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

    public int getNewPostCount() {
        return newPostCount;
    }

    public void setNewPostCount(int newPostCount) {
        this.newPostCount = newPostCount;
    }

    public int getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(int totalPostCount) {
        this.totalPostCount = totalPostCount;
    }
}
