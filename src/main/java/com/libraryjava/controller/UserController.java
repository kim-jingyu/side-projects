package com.libraryjava.controller;

import com.libraryjava.domain.user.User;
import com.libraryjava.dto.user.UserMakeDto;
import com.libraryjava.dto.user.UserResponseDto;
import com.libraryjava.dto.user.UserUpdateDto;
import com.libraryjava.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @PostMapping("/user")
    public ResponseEntity<Void> makeUser(@RequestBody UserMakeDto userMakeDto) {
        userService.makeUser(userMakeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.updateUser(userUpdateDto);
            return ResponseEntity
                    .ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@RequestParam String name) {
        try {
            userService.deleteUser(name);
            return ResponseEntity
                    .ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}
