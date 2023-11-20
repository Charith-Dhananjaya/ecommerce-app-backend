package com.emart.user.controller;

import com.emart.user.dto.UserLoginRequest;
import com.emart.user.dto.UserRequest;
import com.emart.user.dto.UserResponse;
import com.emart.user.model.User;
import com.emart.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody UserRequest userRequest) {
        return userService.signUp(userRequest);
    }

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody UserLoginRequest userLoginRequest) {
        boolean isSigned = userService.signIn(userLoginRequest);
        return isSigned? "Logged in Successfully!" :"Authentication failed";
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserResponse> getUserById(@RequestParam String id) {
        UserResponse userResponse = userService.getUserById(id);
        if (userResponse != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllUsers")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/updateUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteUser(@PathVariable(value = "id") String id) {
        return userService.deleteUser(id);
    }
}
