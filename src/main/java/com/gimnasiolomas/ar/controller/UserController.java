package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.LoginDTO;
import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.WeekDay;
import com.gimnasiolomas.ar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> findAll(){
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable long id){
        return userService.findById(id);
    }
    @GetMapping("/current")
    public UserDto activeUser(Authentication authentication){
        return userService.findByEmail(authentication);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok("User logged in correctly");
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

    @PostMapping("/activity")
    public ResponseEntity<?> createUserActivitySchedule(Authentication authentication,
                                                        @RequestParam @NotBlank String activityName,
                                                        @RequestParam @NotBlank WeekDay weekDay,
                                                        @RequestParam @NotBlank int hour){
        return userService.createUserActivitySchedule(authentication, activityName, weekDay, hour);
    }
}
