package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClassGroupRequestDto {
    private int id;

    @NotBlank(message = "Name should not be blank")
    private String name;

    private String reference;

    @NotNull(message = "LevelOfStudy id should not be null")
    @Min(value = 1, message = "LevelOfStudy id should be greater than or equal to 1")
    private Integer levelOfStudyId;

    public ClassGroupRequestDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Name should not be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name should not be blank") String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public @NotNull(message = "LevelOfStudy id should not be null") @Min(value = 1, message = "LevelOfStudy id should be greater than or equal to 1") Integer getLevelOfStudyId() {
        return levelOfStudyId;
    }

    public void setLevelOfStudyId(@NotNull(message = "LevelOfStudy id should not be null") @Min(value = 1, message = "LevelOfStudy id should be greater than or equal to 1") Integer levelOfStudyId) {
        this.levelOfStudyId = levelOfStudyId;
    }

    public ClassGroupRequestDto(int id, String name, String reference, Integer levelOfStudyId) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.levelOfStudyId = levelOfStudyId;
    }
}
