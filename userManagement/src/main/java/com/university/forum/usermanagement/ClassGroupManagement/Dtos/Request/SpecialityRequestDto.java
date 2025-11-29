package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SpecialityRequestDto {

    private int id;

    @NotBlank(message = "Name should not be blank")
    private String name;

    private String reference;

    @NotNull(message = "Branch id should not be null")
    @Min(value = 1, message = "Branch id should be greater than or equal to 1")
    private Integer branchId;


    public SpecialityRequestDto() {}
    public SpecialityRequestDto(int id, String name, String reference, Integer branchId) {}

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

    public @NotNull(message = "Speciality id should not be null") @Min(value = 1, message = "Speciality id should be greater than or equal to 1") Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(@NotNull(message = "Speciality id should not be null") @Min(value = 1, message = "Speciality id should be greater than or equal to 1") Integer branchId) {
        this.branchId = branchId;
    }
}
