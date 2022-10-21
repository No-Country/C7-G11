package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.UserActivityScheduleDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.dto.UserPlanDTO;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.error.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(Authentication authentication);
    List<UserDTO> findAll();
    UserDTO saveUser(User user) throws UnderLegalAgeException;
    UserDTO findById(long id);
    UserActivityScheduleDTO createUserActivitySchedule(Authentication authentication, InscriptionDTO inscriptionDTO) throws ActivityAlreadyScheduledException, NoActivityFoundException, NoGymClassesLeftException;

    UserPlanDTO assignNewPlan(Authentication authentication, String planName) throws PlanNotFoundException;
}

