package com.codecafe.bookshop.user;

public class UserTestBuilder {

    private final User.UserBuilder userBuilder;

    public UserTestBuilder() {
        userBuilder = User.builder()
                .id(1L)
                .email("test@test.com")
                .password("password")
                .role(Role.USER);
    }

    public static CreateUserRequest buildCreateUserRequest() {
        return new CreateUserRequest("test@test.com", "password");
    }

    public User build() {
        return userBuilder.build();
    }

    public UserTestBuilder withEmail(String email) {
        userBuilder.email(email);
        return this;
    }

}