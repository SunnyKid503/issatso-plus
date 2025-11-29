package com.university.forum.forummanagement.forums.models;

public enum PostBaseType {
    POST("POST"),
    EVENT("EVENT");

    private final String value;

    PostBaseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public static PostBaseType fromValue(String value) {
        for (PostBaseType type : PostBaseType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown post base type: " + value);
    }
}
