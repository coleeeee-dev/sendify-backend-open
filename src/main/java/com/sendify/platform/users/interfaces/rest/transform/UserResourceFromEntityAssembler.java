package com.sendify.platform.users.interfaces.rest.transform;

import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user) {
        String createdAt = user.getCreatedAt() != null
                ? user.getCreatedAt().toString()
                : null;

        return new UserResource(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole().getValue(),
                user.getAvatar(),
                user.isActive(),
                createdAt
        );
    }
}
