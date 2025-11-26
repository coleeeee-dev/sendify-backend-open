package com.sendify.platform.users.domain.services;

import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.commands.LoginCommand;
import com.sendify.platform.users.domain.model.valueobjects.LoginResult;

import java.util.Optional;

public interface AuthService {

    LoginResult login(LoginCommand command);

    void logout(String token);

    Optional<User> getUserFromToken(String token);
}
