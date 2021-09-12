package com.codecafe.bookshop.user;

import com.codecafe.bookshop.error.exception.UserAlreadyExistsException;
import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.Role;
import com.codecafe.bookshop.user.model.UpdateRoleRequest;
import com.codecafe.bookshop.user.persistence.UserEntity;
import com.codecafe.bookshop.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.Validator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserService userService = new UserService();

    @Test
    void shouldCreateUserWithValidInputs() {
        CreateUserRequest createUserRequest = new CreateUserRequestTestBuilder().build();
        UserEntity userEntity = new UserTestBuilder().withEmail(createUserRequest.getEmail()).build();
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserEntity createdUserEntity = userService.createUser(createUserRequest);

        ArgumentCaptor<UserEntity> argCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(argCaptor.capture());
        assertEquals(createUserRequest.getEmail(), argCaptor.getValue().getEmail());
        assertEquals(userEntity.getId(), createdUserEntity.getId());
        assertEquals(userEntity.getEmail(), createdUserEntity.getEmail());
    }

    @Test
    void shouldNotCreateUserWhenUserAlreadyExists() {
        CreateUserRequest createUserRequest = new CreateUserRequestTestBuilder().build();
        when(userRepository.findByEmail(createUserRequest.getEmail())).thenReturn(Optional.of(new UserEntity()));
        userRepository.save(UserEntity.createFrom(createUserRequest));
        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserRequest));

        assertEquals("A user with this email already exists", ex.getMessage());
    }

    @Test
    void shouldLoadUserByEmail() {
        UserEntity userEntity = new UserTestBuilder().build();
        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername("test@test.com");

        assertEquals("test@test.com", userDetails.getUsername());
    }

    @Test
    void shouldThrowUserNotFoundExceptionIfEmailNotFound() {
        UpdateRoleRequest updatedRoleRequest = new UpdateRoleRequest("test@test.com", Role.ADMIN);

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class, () -> userService.updateRole(updatedRoleRequest));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void shouldUpdateRole() {
        UserEntity userEntity = new UserTestBuilder().build();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userEntity));

        UpdateRoleRequest updatedRoleRequest = new UpdateRoleRequest("test@test.com", Role.ADMIN);

        assertDoesNotThrow(() -> userService.updateRole(updatedRoleRequest));
        ArgumentCaptor<UserEntity> argCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository, times(1)).save(argCaptor.capture());
    }

}