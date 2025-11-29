package com.university.forum.usermanagement.ClassGroupManagement.Dtos.Request;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDto {

    private int id;
    @NotBlank(message = "Name should not be blank")
    private String name;
    private String reference;




    public DepartmentRequestDto() {
    }

    public DepartmentRequestDto(int id, String name, String reference) {
        this.id = id;
        this.name = name;
        this.reference = reference;
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
}
