package com.codecafe.bookshop.user.model;

import lombok.Builder;

@Builder
public record CreateUserRequest(String email, String password) {
}