package com.sendify.platform.users.interfaces.rest;

import com.sendify.platform.users.domain.exceptions.InvalidCredentialsException;
import com.sendify.platform.users.domain.model.commands.LoginCommand;
import com.sendify.platform.users.domain.model.valueobjects.LoginResult;
import com.sendify.platform.users.domain.services.AuthService;
import com.sendify.platform.users.interfaces.rest.resources.LoginResource;
import com.sendify.platform.users.interfaces.rest.resources.LoginResponseResource;
import com.sendify.platform.users.interfaces.rest.transform.LoginCommandFromResourceAssembler;
import com.sendify.platform.users.interfaces.rest.transform.LoginResponseResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseResource> login(
            @Valid @RequestBody LoginResource resource) {

        LoginCommand command =
                LoginCommandFromResourceAssembler.toCommandFromResource(resource);

        LoginResult result;
        try {
            result = authService.login(command);
        } catch (InvalidCredentialsException ex) {
            throw new ResponseStatusException(UNAUTHORIZED, ex.getMessage());
        }

        LoginResponseResource response =
                LoginResponseResourceFromEntityAssembler.toResource(
                        result.user(),
                        result.token()
                );

        return ResponseEntity.ok(response);
    }

    // POST /auth/logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {

        String token = extractToken(authorizationHeader);
        if (token == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid or missing token");
        }

        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) return null;
        if (!authorizationHeader.startsWith("Bearer ")) return null;
        return authorizationHeader.substring("Bearer ".length()).trim();
    }
}
