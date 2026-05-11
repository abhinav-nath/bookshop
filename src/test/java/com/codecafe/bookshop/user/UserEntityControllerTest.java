package com.codecafe.bookshop.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.codecafe.bookshop.error.exception.UserAlreadyExistsException;
import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.UpdateRoleRequest;
import com.codecafe.bookshop.user.persistence.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.codecafe.bookshop.user.UserTestBuilder.buildCreateUserRequest;
import static com.codecafe.bookshop.user.UserTestBuilder.buildUpdateRoleRequest;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser
public class UserEntityControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldCreateUserWhenValidCredentialsAreProvided() throws Exception {
        String email = "test@test.com";
        CreateUserRequest createUserRequest = buildCreateUserRequest();
        UserEntity userEntity = new UserTestBuilder().withEmail(email).build();
        when(userService.createUser(createUserRequest)).thenReturn(userEntity);

        mockMvc.perform(post("/user")
                   .with(csrf())
                   .content(objectMapper.writeValueAsString(createUserRequest))
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(userEntity.getId()))
               .andExpect(jsonPath("$.email").value(email));

        verify(userService, times(1)).createUser(createUserRequest);
    }

    @Test
    void shouldRespondWith400WhenUserAlreadyExists() throws Exception {
        CreateUserRequest createUserRequest = buildCreateUserRequest();
        when(userService.createUser(createUserRequest)).thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(post("/user")
                   .with(csrf())
                   .content(objectMapper.writeValueAsString(createUserRequest))
                   .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value("A user with this email already exists"));
    }

    @Test
    void shouldRespondWith404OnUpdateRoleWhenUserNotFound() throws Exception {
        UpdateRoleRequest updateRoleRequest = buildUpdateRoleRequest();

        // need to use doThrow() for void methods
        doThrow(new UsernameNotFoundException("User not found"))
            .when(userService).updateRole(updateRoleRequest);
    }
}