package com.codecafe.bookshop.user;

import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.Role;
import com.codecafe.bookshop.user.model.UpdateRoleRequest;
import com.codecafe.bookshop.user.persistence.UserEntity;

public class UserTestBuilder {

    private final UserEntity.UserEntityBuilder userEntityBuilder;

    public UserTestBuilder() {
        userEntityBuilder = UserEntity.builder()
                .id(1L)
                .email("test@test.com")
                .password("password")
                .role(Role.USER);
    }

    public static CreateUserRequest buildCreateUserRequest() {
        return new CreateUserRequest("test@test.com", "password");
    }

    public static UpdateRoleRequest buildUpdateRoleRequest() {
        return new UpdateRoleRequest("test@test.com", Role.ADMIN);
    }

    public UserEntity build() {
        return userEntityBuilder.build();
    }

    public UserTestBuilder withEmail(String email) {
        userEntityBuilder.email(email);
        return this;
    }

}