package com.sendify.platform.users.domain.services;

import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.commands.CreateUserCommand;
import com.sendify.platform.users.domain.model.commands.DeleteUserCommand;
import com.sendify.platform.users.domain.model.commands.UpdateUserCommand;

public interface UsersCommandService {

    User handle(CreateUserCommand command);

    User handle(UpdateUserCommand command);

    void handle(DeleteUserCommand command);
}
