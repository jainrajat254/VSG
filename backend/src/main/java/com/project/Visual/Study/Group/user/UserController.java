package com.project.Visual.Study.Group.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginCredentials credentials) {
        return userService.login(credentials);
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> login(@RequestBody RegisterCredentials credentials) {
        return userService.register(credentials);
    }
}
