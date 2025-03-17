package org.example.jobsearch.controller;

import org.example.jobsearch.dto.AvatarUploadDto;
import org.example.jobsearch.dto.UserDto;
import org.example.jobsearch.model.User;
import org.example.jobsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(@ModelAttribute AvatarUploadDto avatarUploadDto) {
        try {
            String fileName = userService.uploadAvatar(avatarUploadDto);
            if (fileName != null) {
                return new ResponseEntity<>(fileName, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Ошибка при загрузке аватара", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}