package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.entity.User;
import com.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.rest.webservices.restfulwebservices.repository.UserDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDaoService userDaoService;

    @GetMapping(value = "/users")
    public List<User> getUserList() {
        return userDaoService.findAll();
    }

    // EntityModel
    // WebMvcLinkBuilder
    @GetMapping(value = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {
        User user = userDaoService.findOne(id);

        if (user == null) {
            throw new UserNotFoundException("id = " + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUserList());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> saveUser(@Validated @RequestBody User user) {
        User savedUser = userDaoService.save(user);
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
        userDaoService.deleteById(id);
    }
}
