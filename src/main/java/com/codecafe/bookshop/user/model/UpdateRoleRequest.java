package com.codecafe.bookshop.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateRoleRequest {

    private final String email;
    private final Role role;

}