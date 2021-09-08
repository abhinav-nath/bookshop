package com.codecafe.bookshop.user.persistence;

import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import javax.persistence.*;

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

        if (StringUtils.isNotEmpty(createUserRequest.getPassword()))
            password = PASSWORD_ENCODER.encode(createUserRequest.getPassword());

        return new UserEntity(createUserRequest.getEmail(), password);
    }

    public void updateRole(Role role) {
        this.role = role;
    }

}