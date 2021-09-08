package com.codecafe.bookshop.user;

import com.codecafe.bookshop.user.model.CreateUserRequest;
import com.codecafe.bookshop.user.model.CreateUserResponse;
import com.codecafe.bookshop.user.model.UpdateRoleRequest;
import com.codecafe.bookshop.user.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserEntity userEntity = userService.createUser(createUserRequest);
        return new ResponseEntity<>(new CreateUserResponse(userEntity), HttpStatus.CREATED);
    }

    @PutMapping("/admin/user/role")
    ResponseEntity<Void> updateRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
        userService.updateRole(updateRoleRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}