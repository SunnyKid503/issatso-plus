package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Response;

public class BranchResponseDto {

    private int id;
    private String name;
    private String reference;
    private DepartmentResponseDto department;

    public BranchResponseDto() {
    }

    public BranchResponseDto(int id, String name, String reference, DepartmentResponseDto department) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.department = department;
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

    public DepartmentResponseDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentResponseDto department) {
        this.department = department;
    }
}
