package com.sendify.platform.users.application.internal.commandservices;

import com.sendify.platform.users.domain.exceptions.UserNotFoundException;
import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.commands.CreateUserCommand;
import com.sendify.platform.users.domain.model.commands.DeleteUserCommand;
import com.sendify.platform.users.domain.model.commands.UpdateUserCommand;
import com.sendify.platform.users.domain.model.valueobjects.UserRole;
import com.sendify.platform.users.domain.services.UsersCommandService;
import com.sendify.platform.users.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsersCommandServiceImpl implements UsersCommandService {

    private final UserRepository userRepository;

    public UsersCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User handle(CreateUserCommand command) {

        if (userRepository.existsByEmail(command.email().trim().toLowerCase())) {
            throw new IllegalArgumentException("Email already in use");
        }

        var role = command.role() != null
                ? new UserRole(command.role())
                : new UserRole("VIEWER");

        var user = User.create(
                command.email(),
                command.name(),
                role,
                command.avatar(),
                command.password()
        );

        return userRepository.save(user);
    }

    @Override
    public User handle(UpdateUserCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new UserNotFoundException(command.userId()));

        UserRole role = null;
        if (command.role() != null && !command.role().isBlank()) {
            role = new UserRole(command.role());
        }

        user.update(
                command.name(),
                role,
                command.avatar(),
                command.isActive()
        );

        return userRepository.save(user);
    }

    @Override
    public void handle(DeleteUserCommand command) {
        if (!userRepository.existsById(command.userId())) {
            throw new UserNotFoundException(command.userId());
        }
        userRepository.deleteById(command.userId());
    }
}
