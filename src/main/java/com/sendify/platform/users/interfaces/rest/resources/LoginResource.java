package com.sendify.platform.users.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginResource(
        @NotBlank @Email
        String email,
        @NotBlank
        String password
) {
}
