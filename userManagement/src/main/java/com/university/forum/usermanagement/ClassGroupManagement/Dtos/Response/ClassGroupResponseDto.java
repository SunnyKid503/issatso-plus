package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response;

public class ClassGroupResponseDto {
    private int id;
    private String name;
    private String reference;
    private LevelOfStudyResponseDto levelOfStudy;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LevelOfStudyResponseDto getLevelOfStudy() {
        return levelOfStudy;
    }

    public void setLevelOfStudy(LevelOfStudyResponseDto levelOfStudy) {
        this.levelOfStudy = levelOfStudy;
    }

    public ClassGroupResponseDto() {
    }

    public ClassGroupResponseDto(int id, String name, String reference, LevelOfStudyResponseDto levelOfStudy) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.levelOfStudy = levelOfStudy;
    }
}
