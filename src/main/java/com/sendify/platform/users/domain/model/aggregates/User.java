package com.sendify.platform.users.domain.model.aggregates;

import com.sendify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.sendify.platform.users.domain.model.valueobjects.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 140)
    private String email;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Embedded
    private UserRole role;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    private User(String email,
                 String name,
                 UserRole role,
                 String avatar,
                 String rawPassword) {

        setEmail(email);
        setName(name);
        this.role = role != null ? role : new UserRole("VIEWER");
        this.avatar = avatar != null && !avatar.isBlank() ? avatar.trim() : null;
        setPassword(rawPassword);
        this.isActive = true;
    }

    public static User create(String email,
                              String name,
                              UserRole role,
                              String avatar,
                              String rawPassword) {
        return new User(email, name, role, avatar, rawPassword);
    }

    public void update(String name,
                       UserRole role,
                       String avatar,
                       Boolean isActive) {
        if (name != null && !name.isBlank()) {
            setName(name);
        }
        if (role != null) {
            this.role = role;
        }
        if (avatar != null) {
            this.avatar = avatar.isBlank() ? null : avatar.trim();
        }
        if (isActive != null) {
            this.isActive = isActive;
        }
    }

    public boolean checkPassword(String rawPassword) {
        if (rawPassword == null) return false;
        return this.passwordHash.equals(hash(rawPassword));
    }

    public void changePassword(String rawPassword) {
        setPassword(rawPassword);
    }

    private void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        String trimmed = email.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = trimmed;
    }

    private void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name = name.trim();
    }

    private void setPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password must not be blank");
        }
        this.passwordHash = hash(rawPassword);
    }

    private String hash(String rawPassword) {
        // Hash muy simple SOLO para el laboratorio (no usar en producción).
        return Base64.getEncoder().encodeToString(
                rawPassword.getBytes(StandardCharsets.UTF_8)
        );
    }
}
