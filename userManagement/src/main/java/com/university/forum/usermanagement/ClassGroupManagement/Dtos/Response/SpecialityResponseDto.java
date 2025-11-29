package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response;

public class SpecialityResponseDto {

    private int id;
    private String name;
    private String reference;
    private BranchResponseDto branch;


    public SpecialityResponseDto() {
    }

    public SpecialityResponseDto(int id, String name, String reference, BranchResponseDto branch) {
    }

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

    public BranchResponseDto getBranch() {
        return branch;
    }

    public void setBranch(BranchResponseDto branch) {
        this.branch = branch;
    }
}
