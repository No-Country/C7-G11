package com.gimnasiolomas.ar.restriction;

import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepository userRepository;


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByEmail(s.toLowerCase());
        return user == null;
    }
}
