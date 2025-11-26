package com.sendify.platform.users.interfaces.rest.transform;

import com.sendify.platform.users.domain.model.commands.CreateUserCommand;
import com.sendify.platform.users.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {

    public static CreateUserCommand toCommandFromResource(
            CreateUserResource resource) {

        return new CreateUserCommand(
                resource.email(),
                resource.name(),
                resource.role(),
                resource.avatar(),
                resource.password()
        );
    }
}
