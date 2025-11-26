package com.sendify.platform.users.interfaces.rest.resources;

public record UserResource(
        Long id,
        String email,
        String name,
        String role,
        String avatar,
        boolean isActive,
        String createdAt
) {
}
