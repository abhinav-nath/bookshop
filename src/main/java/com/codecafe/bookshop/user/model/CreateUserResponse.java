package com.codecafe.bookshop.user.model;

import com.codecafe.bookshop.user.persistence.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserResponse {

    private final Long id;
    private final String email;

    public CreateUserResponse(UserEntity that) {
        this.id = that.getId();
        this.email = that.getEmail();
    }

}