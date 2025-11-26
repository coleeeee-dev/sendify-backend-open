package com.sendify.platform.users.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserResource(
        @NotBlank @Email
        String email,

        @NotBlank
        String name,

        @NotBlank
        String role,

        String avatar,

        @NotBlank
        String password
) {
}
