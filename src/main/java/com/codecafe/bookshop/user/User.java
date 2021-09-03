package com.codecafe.bookshop.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

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

    private User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public static User createFrom(CreateUserRequest createUserRequest) {
        String password = "";

        if (StringUtils.isNotEmpty(createUserRequest.getPassword()))
            password = PASSWORD_ENCODER.encode(createUserRequest.getPassword());

        return new User(createUserRequest.getEmail(), password);
    }

    public void updateRole(Role role) {
        this.role = role;
    }

}