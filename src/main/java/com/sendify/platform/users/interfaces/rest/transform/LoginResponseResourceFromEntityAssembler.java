package com.sendify.platform.users.interfaces.rest.transform;

import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.interfaces.rest.resources.LoginResponseResource;
import com.sendify.platform.users.interfaces.rest.resources.UserResource;

public class LoginResponseResourceFromEntityAssembler {

    public static LoginResponseResource toResource(User user, String token) {
        UserResource userResource =
                UserResourceFromEntityAssembler.toResourceFromEntity(user);
        return new LoginResponseResource(userResource, token);
    }
}
