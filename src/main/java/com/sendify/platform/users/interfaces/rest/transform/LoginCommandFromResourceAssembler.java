package com.sendify.platform.users.interfaces.rest.transform;

import com.sendify.platform.users.domain.model.commands.LoginCommand;
import com.sendify.platform.users.interfaces.rest.resources.LoginResource;

public class LoginCommandFromResourceAssembler {

    public static LoginCommand toCommandFromResource(LoginResource resource) {
        return new LoginCommand(resource.email(), resource.password());
    }
}
