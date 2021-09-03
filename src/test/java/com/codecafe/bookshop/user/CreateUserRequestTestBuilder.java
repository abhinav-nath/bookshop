package com.codecafe.bookshop.user;

public class CreateUserRequestTestBuilder {

    private CreateUserRequest.CreateUserRequestBuilder requestBuilder;

    public CreateUserRequestTestBuilder() {
        requestBuilder = CreateUserRequest.builder()
                .email("test@test.com")
                .password("password");
    }

    CreateUserRequest build() {
        return requestBuilder.build();
    }

    public CreateUserRequestTestBuilder withEmptyEmail() {
        requestBuilder.email("");
        return this;
    }

    public CreateUserRequestTestBuilder withEmptyPassword() {
        requestBuilder.password("");
        return this;
    }

    public CreateUserRequestTestBuilder withInvalidEmail() {
        requestBuilder.email("abc#xyz.com").password("password");
        return this;
    }

}