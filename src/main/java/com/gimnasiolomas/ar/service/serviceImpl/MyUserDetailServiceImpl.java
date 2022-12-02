package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.entity.MyUserDetails;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.repository.UserRepository;
import com.gimnasiolomas.ar.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServiceImpl implements MyUserDetailService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(Messages.USER_NOT_FOUND));
        return new MyUserDetails(user);
    }
}
