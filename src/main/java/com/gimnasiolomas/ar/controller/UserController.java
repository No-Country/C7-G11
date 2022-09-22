package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.repository.UserRepository;
import com.gimnasiolomas.ar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> findAll(){
        return userService.findAll();
    }
    @GetMapping("/current")
    public UserDto activeUser(Authentication authentication){
        return userService.findByEmail(authentication);
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.saveUser(userDto));
    }

}
