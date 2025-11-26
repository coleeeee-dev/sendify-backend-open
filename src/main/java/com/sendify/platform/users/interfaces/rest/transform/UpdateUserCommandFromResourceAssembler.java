package com.sendify.platform.users.interfaces.rest.transform;

import com.sendify.platform.users.domain.model.commands.UpdateUserCommand;
import com.sendify.platform.users.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {

    public static UpdateUserCommand toCommandFromResource(
            Long userId,
            UpdateUserResource resource) {

        return new UpdateUserCommand(
                userId,
                resource.name(),
                resource.role(),
                resource.avatar(),
                resource.isActive()
        );
    }
}
