package com.sendify.platform.users.domain.model.commands;

public record LoginCommand(
        String email,
        String password
) {
}
