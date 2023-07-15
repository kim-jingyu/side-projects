package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.entity.User;
import com.rest.webservices.restfulwebservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findOne(id);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> saveUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }
}
