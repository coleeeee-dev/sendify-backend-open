package com.sendify.platform.users.application.internal.queryservices;

import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.queries.GetAllUsersQuery;
import com.sendify.platform.users.domain.model.queries.GetCurrentUserQuery;
import com.sendify.platform.users.domain.model.queries.GetUserByIdQuery;
import com.sendify.platform.users.domain.services.UsersQueryService;
import com.sendify.platform.users.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersQueryServiceImpl implements UsersQueryService {

    private final UserRepository userRepository;

    public UsersQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetCurrentUserQuery query) {
        if (query.userId() == null) return Optional.empty();
        return userRepository.findById(query.userId());
    }
}
