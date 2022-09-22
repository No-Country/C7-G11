package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDto findByEmail(Authentication authentication);
    List<UserDto> findAll();
    UserDto saveUser(UserDto userDto);
}
