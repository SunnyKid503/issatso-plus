package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BranchRequestDto {

    private int id;

    @NotBlank(message = "Name should not be blank")
    private String name;

    private String reference;

    @NotNull(message = "Department id should not be null")
    @Min(value = 1, message = "Department id should be greater than or equal to 1")
    private Integer departmentId;

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

    public @NotNull(message = "Department id should not be null") @Min(value = 1, message = "Department id should be greater than or equal to 1") Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(@NotNull(message = "Department id should not be null") @Min(value = 1, message = "Department id should be greater than or equal to 1") Integer departmentId) {
        this.departmentId = departmentId;
    }

    public BranchRequestDto() {
    }

    public BranchRequestDto(int id, String name, String reference, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.departmentId = departmentId;
    }
}
