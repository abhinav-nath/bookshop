package com.codecafe.bookshop.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import static com.codecafe.bookshop.user.UserTestBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUserWhenValidCredentialsAreProvided() throws Exception {
        String email = "test@test.com";
        CreateUserRequest createUserRequest = buildCreateUserRequest();
        User user = new UserTestBuilder().withEmail(email).build();
        when(userService.createUser(createUserRequest)).thenReturn(user);
        CreateUserResponse createUserResponse = CreateUserResponse.builder().id(user.getId()).email(email).build();

        mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(createUserResponse)));

        verify(userService, times(1)).createUser(createUserRequest);
    }

    @Test
    void shouldRespondWith400WhenUserAlreadyExists() throws Exception {
        CreateUserRequest createUserRequest = buildCreateUserRequest();
        when(userService.createUser(createUserRequest)).thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(post("/user")
                        .content(objectMapper.writeValueAsString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("A user with this email already exists"));
    }

    @Test
    void shouldRespondWith404OnUpdateRoleWhenUserNotFound() throws Exception {
        UpdateRoleRequest updateRoleRequest = buildUpdateRoleRequest();

        doThrow(new UsernameNotFoundException("User not found"))
                .when(userService).updateRole(updateRoleRequest);
    }

}