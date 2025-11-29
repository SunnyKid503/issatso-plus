package com.university.forum.usermanagement.MemberManagement.Models.Enums;

public enum MemberType {
    STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR"),
    ADMINISTRATOR("ADMINISTRATOR"),
    ASSOCIATION("ASSOCIATION");

    private final String value;

    MemberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public static MemberType fromValue(String value) {
        for (MemberType type : MemberType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown member type: " + value);
    }
}