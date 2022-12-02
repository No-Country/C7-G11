package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.*;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.error.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDTO findByEmail(Authentication authentication);
    List<UserDTO> findAll();
    UserDTO saveUser(UserRegisterDTO userRegisterDTO) throws UnderLegalAgeException;
    UserDTO findById(long id) throws NotFoundException;
    UserActivityScheduleDTO createUserActivitySchedule(Authentication authentication, InscriptionDTO inscriptionDTO) throws ActivityAlreadyScheduledException, NoActivityFoundException, NoGymClassesLeftException, NotFoundException, HolidayException, MaxNumberOfMembersReachedException;
    UserPlanDTO assignNewPlan(Authentication authentication, String planName) throws PlanNotFoundException, ActivePlanException, NotFoundException;
    String deleteUser(String email) throws NotFoundException;
    UserActivityScheduleDTO cancelUserActivitySchedule(Authentication authentication, UserActivityScheduleDTO userActivityScheduleDTO) throws NotFoundException, CancelActivityScheduleException, CancelInscriptionException;

}

