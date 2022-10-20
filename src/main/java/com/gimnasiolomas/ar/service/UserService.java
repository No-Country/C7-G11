package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.dto.UserPlanDTO;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.error.PlanNotFoundException;
import com.gimnasiolomas.ar.error.UnderLegalAgeException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(Authentication authentication);
    List<UserDTO> findAll();
    UserDTO saveUser(User user) throws UnderLegalAgeException;
    UserDTO findById(long id);
    ResponseEntity<?> createUserActivitySchedule(Authentication authentication, InscriptionDTO inscriptionDTO);

    UserPlanDTO assignNewPlan(Authentication authentication, String planName) throws PlanNotFoundException;
}

