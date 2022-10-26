package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.*;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.error.*;
import com.gimnasiolomas.ar.service.EmailSenderService;
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
import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public UserDTO findById(@PathVariable long id)
            throws NotFoundException {
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

        return ResponseEntity.ok("Usuario autenticado exitosamente");
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserRegisterDTO userRegisterDTO)
            throws UnderLegalAgeException {
        return ResponseEntity.ok(userService.saveUser(userRegisterDTO));
    }

    @PostMapping("/userplan")
    public ResponseEntity<?> getUserPlan(Authentication authentication,
                                         @RequestParam String planName)
            throws PlanNotFoundException,
            ActivePlanException,
            NotFoundException {
        return ResponseEntity.ok(userService.assignNewPlan(authentication, planName));
    }

    @PostMapping("/activity")
    public ResponseEntity<?> createUserActivitySchedule(Authentication authentication,
                                                        @Validated @RequestBody InscriptionDTO inscriptionDTO)
            throws ActivityAlreadyScheduledException,
            NoActivityFoundException,
            NoGymClassesLeftException,
            NotFoundException,
            HolidayException,
            MaxNumberOfMembersReachedException {
        return ResponseEntity.ok(userService.createUserActivitySchedule(authentication, inscriptionDTO));
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String email) throws NotFoundException {
        return ResponseEntity.ok(userService.deleteUser(email));
    }

    @PatchMapping
    public ResponseEntity<?> cancelUserActivitySchedule(Authentication authentication,
                                                        @RequestBody UserActivityScheduleDTO userActivityScheduleDTO)
            throws NotFoundException,
            CancelActivityScheduleException,
            CancelInscriptionException {
        return ResponseEntity.ok(userService.cancelUserActivitySchedule(authentication, userActivityScheduleDTO));
    }
}
