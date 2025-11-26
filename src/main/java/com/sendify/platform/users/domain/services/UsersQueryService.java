package com.sendify.platform.users.domain.services;

import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.queries.GetAllUsersQuery;
import com.sendify.platform.users.domain.model.queries.GetCurrentUserQuery;
import com.sendify.platform.users.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UsersQueryService {

    List<User> handle(GetAllUsersQuery query);

    Optional<User> handle(GetUserByIdQuery query);

    Optional<User> handle(GetCurrentUserQuery query);
}
