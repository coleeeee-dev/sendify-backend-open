package com.sendify.platform.users.domain.model.commands;

public record CreateUserCommand(
        String email,
        String name,
        String role,
        String avatar,
        String password
) {
}
