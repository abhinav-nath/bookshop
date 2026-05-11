package com.codecafe.bookshop.user;

import java.util.Optional;

import jakarta.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

import com.codecafe.bookshop.error.exception.UserAlreadyExistsException;
import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.UpdateRoleRequest;
import com.codecafe.bookshop.user.persistence.UserEntity;
import com.codecafe.bookshop.user.persistence.UserRepository;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(CreateUserRequest createUserRequest) {
        Optional<UserEntity> user = userRepository.findByEmail(createUserRequest.email());

        if (user.isPresent())
            throw new UserAlreadyExistsException();

        UserEntity newUserEntity = UserEntity.createFrom(createUserRequest);
        validator.validate(newUserEntity);
        return userRepository.save(newUserEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
            userEntity.getEmail(),
            userEntity.getPassword(),
            AuthorityUtils.createAuthorityList(userEntity.getRole().name())
        );
    }

    public void updateRole(UpdateRoleRequest updateRoleRequest) {
        UserEntity userEntity = userRepository.findByEmail(updateRoleRequest.email())
                                              .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userEntity.updateRole(updateRoleRequest.role());
        userRepository.save(userEntity);
    }
}