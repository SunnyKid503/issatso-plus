package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response;

public class LevelOfStudyResponseDto {


    private int id;
    private String name;
    private String reference;
    private SpecialityResponseDto speciality;

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

    public SpecialityResponseDto getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityResponseDto speciality) {
        this.speciality = speciality;
    }

    public LevelOfStudyResponseDto() {
    }

    public LevelOfStudyResponseDto(int id, String name, String reference, SpecialityResponseDto speciality) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.speciality = speciality;
    }
}
