package com.sendify.platform.users.interfaces.rest;

import com.sendify.platform.users.domain.exceptions.UserNotFoundException;
import com.sendify.platform.users.domain.model.aggregates.User;
import com.sendify.platform.users.domain.model.commands.CreateUserCommand;
import com.sendify.platform.users.domain.model.commands.DeleteUserCommand;
import com.sendify.platform.users.domain.model.commands.UpdateUserCommand;
import com.sendify.platform.users.domain.model.queries.GetAllUsersQuery;
import com.sendify.platform.users.domain.model.queries.GetCurrentUserQuery;
import com.sendify.platform.users.domain.model.queries.GetUserByIdQuery;
import com.sendify.platform.users.domain.services.UsersCommandService;
import com.sendify.platform.users.domain.services.UsersQueryService;
import com.sendify.platform.users.interfaces.rest.resources.CreateUserResource;
import com.sendify.platform.users.interfaces.rest.resources.UpdateUserResource;
import com.sendify.platform.users.interfaces.rest.resources.UserResource;
import com.sendify.platform.users.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.sendify.platform.users.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import com.sendify.platform.users.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.sendify.platform.users.domain.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersCommandService usersCommandService;
    private final UsersQueryService usersQueryService;
    private final AuthService authService;

    public UsersController(UsersCommandService usersCommandService,
                           UsersQueryService usersQueryService,
                           AuthService authService) {
        this.usersCommandService = usersCommandService;
        this.usersQueryService = usersQueryService;
        this.authService = authService;
    }

    // GET /users
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var query = new GetAllUsersQuery();
        List<User> users = usersQueryService.handle(query);

        List<UserResource> resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    // GET /users/{id}
    @GetMapping("{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var query = new GetUserByIdQuery(userId);

        User user = usersQueryService.handle(query)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));

        UserResource resource = UserResourceFromEntityAssembler.toResourceFromEntity(user);
        return ResponseEntity.ok(resource);
    }

    // GET /users/me
    @GetMapping("me")
    public ResponseEntity<UserResource> getCurrentUser(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {

        String token = extractToken(authorizationHeader);

        var userOpt = authService.getUserFromToken(token);

        User user = userOpt.orElseThrow(
                () -> new ResponseStatusException(UNAUTHORIZED, "Invalid or missing token")
        );

        UserResource resource = UserResourceFromEntityAssembler.toResourceFromEntity(user);
        return ResponseEntity.ok(resource);
    }

    // POST /users
    @PostMapping
    public ResponseEntity<UserResource> createUser(
            @Valid @RequestBody CreateUserResource resource) {

        CreateUserCommand command =
                CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);

        User user = usersCommandService.handle(command);

        UserResource userResource =
                UserResourceFromEntityAssembler.toResourceFromEntity(user);

        URI location = URI.create("/api/v1/users/" + user.getId());

        return ResponseEntity.created(location).body(userResource);
    }

    // PATCH /users/{id}
    @PatchMapping("{userId}")
    public ResponseEntity<UserResource> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserResource resource) {

        UpdateUserCommand command =
                UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId, resource);

        User user;
        try {
            user = usersCommandService.handle(command);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }

        UserResource userResource =
                UserResourceFromEntityAssembler.toResourceFromEntity(user);

        return ResponseEntity.ok(userResource);
    }

    // DELETE /users/{id}
    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            usersCommandService.handle(new DeleteUserCommand(userId));
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) return null;
        if (!authorizationHeader.startsWith("Bearer ")) return null;
        return authorizationHeader.substring("Bearer ".length()).trim();
    }
}
