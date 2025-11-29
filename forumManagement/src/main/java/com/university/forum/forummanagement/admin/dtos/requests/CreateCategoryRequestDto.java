package com.university.forum.forummanagement.admin.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCategoryRequestDto {
    @NotNull(message = "Typename should not be null.")
    @NotBlank(message = "Typename should not be blank.")
    private String typename;

    public CreateCategoryRequestDto() {
    }

    public CreateCategoryRequestDto(String typename) {
        this.typename = typename;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
