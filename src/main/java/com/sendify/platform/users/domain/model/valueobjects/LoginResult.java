package com.sendify.platform.users.domain.model.valueobjects;

import com.sendify.platform.users.domain.model.aggregates.User;

public record LoginResult(User user, String token) {
}
