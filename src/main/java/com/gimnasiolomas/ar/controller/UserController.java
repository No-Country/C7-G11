package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.LoginDTO;
import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.repository.UserRepository;
import com.gimnasiolomas.ar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("/current")
    public UserDto activeUser(Authentication authentication){
        return userService.findByEmail(authentication);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (BadCredentialsException ex) {
            return new ResponseEntity(("Username or Password incorrect"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(loginDTO);

    }
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.saveUser(userDto));
    }

}
