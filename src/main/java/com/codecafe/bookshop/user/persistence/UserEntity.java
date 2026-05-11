package com.codecafe.bookshop.user.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.Role;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(message = "Email must follow the pattern abc@xyz.com", regexp = "^.+@.+\\..+$")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public static UserEntity createFrom(CreateUserRequest createUserRequest) {
        String password = "";

        if (StringUtils.isNotEmpty(createUserRequest.password()))
            password = PASSWORD_ENCODER.encode(createUserRequest.password());

        return new UserEntity(createUserRequest.email(), password);
    }

    public void updateRole(Role role) {
        this.role = role;
    }
}