package com.sendify.platform.users.interfaces.rest.resources;

public record LoginResponseResource(
        UserResource user,
        String token
) {
}
