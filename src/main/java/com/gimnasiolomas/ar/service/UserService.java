package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(Authentication authentication);
    List<UserDTO> findAll();
    ResponseEntity<?> saveUser(User user);
    UserDTO findById(long id);
    ResponseEntity<?> createUserActivitySchedule(Authentication authentication, InscriptionDTO inscriptionDTO);

    ResponseEntity<?> getUserPlan(Authentication authentication, String planName);
}

