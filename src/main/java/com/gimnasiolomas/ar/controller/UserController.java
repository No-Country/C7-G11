package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.*;
import com.gimnasiolomas.ar.entity.MyUserDetails;
import com.gimnasiolomas.ar.error.*;
import com.gimnasiolomas.ar.service.EmailSenderService;
import com.gimnasiolomas.ar.service.MyUserDetailService;
import com.gimnasiolomas.ar.service.UserService;
import com.gimnasiolomas.ar.utility.JwtUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

import static com.gimnasiolomas.ar.constants.Messages.ACCOUNT_SID;
import static com.gimnasiolomas.ar.constants.Messages.AUTH_TOKEN;

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
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;

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

        final MyUserDetails myUserDetails = (MyUserDetails) myUserDetailService.loadUserByUsername(loginDTO.getEmail());
        final String jwt = jwtUtil.generateToken(myUserDetails);

        return ResponseEntity.ok(jwt);
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


    @GetMapping("/sms")
    public ResponseEntity<?> sendMessaje(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("+549"),//To -----> Agregar numero. Importante NO agregar el 9!!!!
                        new PhoneNumber("+13023032627"),//From
                        "Holaaaaaa por SMS"//Mensaje
                ).create();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/whatsapp")
    public ResponseEntity<?> sendWAMessaje(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("whatsapp:+549"),//To ----> Agregar numero. Importante agregar el 9!!!!
                new PhoneNumber("whatsapp:+14155238886"),//From
                "Holaaaaaaa desde Whatsapp"//Mensaje
        ).create();
        return ResponseEntity.ok().build();
    }
}
