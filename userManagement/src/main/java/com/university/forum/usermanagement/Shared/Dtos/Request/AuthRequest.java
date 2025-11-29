package com.university.forum.usermanagement.Shared.Dtos.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthRequest {
    @NotBlank(message = "email must be not blank")
    @Email
    @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    private String username;
    @NotBlank(message = "password must be not blank")
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank(message = "email must be not blank") @Email @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "email must be not blank") @Email @Pattern(regexp = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format") String username) {
        this.username = username;
    }

    public @NotBlank(message = "password must be not blank") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "password must be not blank") String password) {
        this.password = password;
    }

    public AuthRequest() {
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
