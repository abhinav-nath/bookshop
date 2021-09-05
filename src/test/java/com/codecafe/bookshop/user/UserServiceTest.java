package com.codecafe.bookshop.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Validator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserService userService = new UserService();

    @Test
    void shouldCreateUserWithValidInputs() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequestTestBuilder().build();
        User user = new UserTestBuilder().withEmail(createUserRequest.getEmail()).build();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(createUserRequest);

        ArgumentCaptor<User> argCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argCaptor.capture());
        assertEquals(createUserRequest.getEmail(), argCaptor.getValue().getEmail());
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    void shouldNotCreateUserWhenUserAlreadyExists() {
        CreateUserRequest createUserRequest = new CreateUserRequestTestBuilder().build();
        when(userRepository.findByEmail(createUserRequest.getEmail())).thenReturn(Optional.of(new User()));
        userRepository.save(User.createFrom(createUserRequest));
        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserRequest));

        assertEquals("A user with this email already exists", ex.getMessage());
    }

    @Test
    void shouldLoadUserByEmail() {
        User user = new UserTestBuilder().build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("test@test.com");

        assertEquals("test@test.com", userDetails.getUsername());
    }

}