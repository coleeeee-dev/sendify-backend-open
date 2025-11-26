package com.sendify.platform.users.interfaces.rest.resources;

public record UpdateUserResource(
        String name,
        String role,
        String avatar,
        Boolean isActive
) {
}
