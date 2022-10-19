package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.LoginDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.service.EmailSenderService;
import com.gimnasiolomas.ar.service.UserService;
import com.gimnasiolomas.ar.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping
    public List<UserDTO> findAll(){
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable long id){
        return userService.findById(id);
    }
    @GetMapping("/current")
    public UserDTO activeUser(Authentication authentication){
        return userService.findByEmail(authentication);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok("User logged in correctly");
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/userplan")
    public ResponseEntity<?> getUserPlan(Authentication authentication,
                                         @RequestParam String planName){
        return userService.getUserPlan(authentication, planName);
    }

    @PostMapping("/activity")
    public ResponseEntity<?> createUserActivitySchedule(Authentication authentication,
                                                        @Validated @RequestBody InscriptionDTO inscriptionDTO){
        return userService.createUserActivitySchedule(authentication, inscriptionDTO);
    }
    //todo ->   planes -> vincular con usuarios
    //todo ->   planes -> crear la propiedad que muestre la cantidad de inscripciones restantes
    //todo ->   inscripcion con dias y horas
    //todo ->   cambiar peticiones de param a body

}
