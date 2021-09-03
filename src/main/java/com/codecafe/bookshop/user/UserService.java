package com.codecafe.bookshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private Validator validator;

    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public User createUser(CreateUserRequest createUserRequest) {
        Optional<User> user = userRepository.findByEmail(createUserRequest.getEmail());

        if (user.isPresent())
            throw new UserAlreadyExistsException();

        User newUser = User.createFrom(createUserRequest);
        validator.validate(newUser);
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getRole().name())
        );
    }

    public void updateRole(UpdateRoleRequest updateRoleRequest) {
        User user = userRepository.findByEmail(updateRoleRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.updateRole(updateRoleRequest.getRole());
        userRepository.save(user);
    }

}