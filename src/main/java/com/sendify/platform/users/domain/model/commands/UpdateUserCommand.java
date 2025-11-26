package com.sendify.platform.users.domain.model.commands;

public record UpdateUserCommand(
        Long userId,
        String name,
        String role,
        String avatar,
        Boolean isActive
) {
}
