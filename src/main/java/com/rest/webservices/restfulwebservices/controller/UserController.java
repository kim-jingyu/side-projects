package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.entity.User;
import com.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.rest.webservices.restfulwebservices.repository.UserDaoService;
import com.rest.webservices.restfulwebservices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    // EntityModel
    // WebMvcLinkBuilder
    @GetMapping(value = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user == null) {
            throw new UserNotFoundException("id = " + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUserList());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> saveUser(@Validated @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
