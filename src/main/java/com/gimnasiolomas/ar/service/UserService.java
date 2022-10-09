package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.WeekDay;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDto findByEmail(Authentication authentication);
    List<UserDto> findAll();
    ResponseEntity<?> saveUser(UserDto userDto);
    UserDto findById(long id);
    ResponseEntity<?> createUserActivitySchedule(Authentication authentication, String activityName, WeekDay weekDay, int hour);
}

