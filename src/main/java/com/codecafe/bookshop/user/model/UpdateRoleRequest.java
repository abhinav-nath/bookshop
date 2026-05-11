package com.codecafe.bookshop.user.model;

import lombok.Builder;

@Builder
public record UpdateRoleRequest(String email, Role role) {
}