package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.dto.UserDto;
import com.gimnasiolomas.ar.entity.*;
import com.gimnasiolomas.ar.error.NotFoundException;
import com.gimnasiolomas.ar.repository.*;
import com.gimnasiolomas.ar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserActivityScheduleRepository userActivityScheduleRepository;
    @Autowired
    private ActivityScheduleRepository activityScheduleRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public UserDto findByEmail(Authentication authentication) {
        return new UserDto(userRepository.findByEmail(authentication.getName()));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> saveUser(UserDto userDto) {
        User user = new User(
                userDto.getName(),
                userDto.getLastName(),
                userDto.getEmail().toLowerCase(),
                passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return ResponseEntity.accepted().body("User created!");
    }

    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
        return new UserDto(user);
    }

    @Override
    public ResponseEntity<?> createUserActivitySchedule(Authentication authentication, String activityName, WeekDay weekDay, int hour) {
        User user = userRepository.findByEmail(authentication.getName());
        if(user == null){
            throw new NotFoundException("User not found");
        }
        Activity activity = activityRepository.findByActivityName(activityName);
        if(activity == null){
            throw new NotFoundException("Activity not found");
        }
        for(ActivitySchedule sch : activity.getActivitySchedules()){
            if(sch.getSchedule().getHour()==hour && sch.getSchedule().getWeekDay()==weekDay){
                UserActivitySchedule userActivitySchedule = new UserActivitySchedule(user, sch);
                userActivityScheduleRepository.save(userActivitySchedule);
                userRepository.save(user);
                return ResponseEntity.accepted().body("Inscripto a la clase de " + activity.getActivityName() + ", el día: " + userActivitySchedule.getActivitySchedule().getSchedule().getWeekDay() + ", a las " + userActivitySchedule.getActivitySchedule().getSchedule().getHour() + " hs.");
            }
        }
        return ResponseEntity.badRequest().body("No existe la actividad " + activityName + " en el horario: " + hour + " hs. el día " + weekDay);
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPassword) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPassword(emailOrPassword, emailOrPassword);

        if(user == null){
            throw new UsernameNotFoundException("ok: false");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getName()));

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), authorities);
    }
}