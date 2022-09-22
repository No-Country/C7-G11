package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.repository.UserRepository;
import com.gimnasiolomas.ar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto findByEmail(Authentication authentication) {
        return new UserDto(userRepository.findByEmail(authentication.getName()));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getLastName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return new UserDto(user);
    }
}