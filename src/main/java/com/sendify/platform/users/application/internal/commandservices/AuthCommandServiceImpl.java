package com.sendify.platform.users.application.internal.commandservices;

import com.sendify.platform.users.domain.exceptions.InvalidCredentialsException;
import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.commands.LoginCommand;
import com.sendify.platform.users.domain.model.valueobjects.LoginResult;
import com.sendify.platform.users.domain.services.AuthService;
import com.sendify.platform.users.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class AuthCommandServiceImpl implements AuthService {

    private final UserRepository userRepository;

    // Token simple en memoria: token -> userId
    private final Map<String, Long> activeTokens = new ConcurrentHashMap<>();

    public AuthCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResult login(LoginCommand command) {

        User user = userRepository.findByEmail(
                        command.email().trim().toLowerCase())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.checkPassword(command.password())) {
            throw new InvalidCredentialsException();
        }

        String token = UUID.randomUUID().toString(); // token tipo UUID, suficiente para el laboratorio
        activeTokens.put(token, user.getId());

        return new LoginResult(user, token);
    }

    @Override
    public void logout(String token) {
        if (token != null) {
            activeTokens.remove(token);
        }
    }

    @Override
    public Optional<User> getUserFromToken(String token) {
        if (token == null || token.isBlank()) return Optional.empty();
        Long userId = activeTokens.get(token);
        if (userId == null) return Optional.empty();
        return userRepository.findById(userId);
    }
}
