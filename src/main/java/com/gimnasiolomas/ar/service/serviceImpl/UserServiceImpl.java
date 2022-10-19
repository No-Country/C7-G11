package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.dto.UserPlanDTO;
import com.gimnasiolomas.ar.entity.*;
import com.gimnasiolomas.ar.error.NotFoundException;
import com.gimnasiolomas.ar.repository.*;
import com.gimnasiolomas.ar.service.EmailSenderService;
import com.gimnasiolomas.ar.service.UserService;
import com.gimnasiolomas.ar.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private EmailSenderService emailSenderService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserPlanRepository userPlanRepository;
    @Override
    public UserDTO findByEmail(Authentication authentication) {
        return new UserDTO(userRepository.findByEmail(authentication.getName()));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> saveUser(User user) {
        User user1 = new User(
                user.getName(),
                user.getLastName(),
                user.getEmail().toLowerCase(),
                passwordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
        String subject = "Bienvenido a Gimnasio Lomas";
        String body = "Gracias por registrarte en nuestra página, bla bla bla";
        emailSenderService.sendEmail(user1.getEmail(), subject, body);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user1));
    }

    @Override
    public UserDTO findById(long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
        return new UserDTO(user);
    }

    @Override
    public ResponseEntity<?> createUserActivitySchedule(Authentication authentication,
                                                        InscriptionDTO inscriptionDTO) {
        User user = userRepository.findByEmail(authentication.getName());
        if(user == null){
            throw new NotFoundException("Usuario no encontrado");
        }
        Activity activity = activityRepository.findByActivityName(inscriptionDTO.getActivityName());
        if(activity == null){
            throw new NotFoundException("Actividad no encontrada");
        }
        UserPlan userPlan = user.getUserPlans().stream().filter(UserPlan::isActive).findFirst().orElse(null);
        if(userPlan==null){
            throw new NotFoundException("Membresía vencída");
        }
        if(userPlan.getGymClassesLeft()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya usaste todas las clases de tu membresía");
        }
        WeekDay weekDay1 = Utility.changeToUpperCase(inscriptionDTO.getWeekDay());
        for(UserActivitySchedule uas : user.getUserActivitySchedules()){
            if(uas.getActivitySchedule().getActivity().getActivityName().equalsIgnoreCase(inscriptionDTO.getActivityName())
                    && uas.getActivitySchedule().getSchedule().getHour()== inscriptionDTO.getHour()
                    && uas.getActivitySchedule().getSchedule().getWeekDay()==weekDay1){
                return ResponseEntity.accepted().body("Ya estabas inscripto para la clase de " + activity.getActivityName() + " el dia " + weekDay1 + " a las " + inscriptionDTO.getHour() + " hs");
            }
        }
        for(ActivitySchedule sch : activity.getActivitySchedules()){
            if(sch.getSchedule().getHour()== inscriptionDTO.getHour() && sch.getSchedule().getWeekDay()==weekDay1){
                UserActivitySchedule userActivitySchedule = new UserActivitySchedule(user, sch);
                userActivityScheduleRepository.save(userActivitySchedule);
//                userRepository.save(user);
                userPlan.substractGymClass();
                userPlanRepository.save(userPlan);
                return ResponseEntity.accepted().body("Inscripto a la clase de " + activity.getActivityName() + ", el día: " + userActivitySchedule.getActivitySchedule().getSchedule().getWeekDay() + ", a las " + userActivitySchedule.getActivitySchedule().getSchedule().getHour() + " hs.");
            }
        }
        return ResponseEntity.badRequest().body("No existe la actividad " + inscriptionDTO.getActivityName() + " en el horario: " + inscriptionDTO.getHour() + " hs. el día " + weekDay1);
    }

    @Override
    public ResponseEntity<?> getUserPlan(Authentication authentication, String planName) {
        User user = userRepository.findByEmail(authentication.getName());
        if(user==null){
            throw new NotFoundException("Usuario no encontrado");
        }
        Plan plan = planRepository.findByName(planName);
        if(plan == null){
            throw new NotFoundException("Plan no encontrado");
        }
        UserPlan userPlan = user.getUserPlans().stream().filter(UserPlan::isActive).findFirst().orElse(null);
        if(userPlan != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya tienes un plan activo");
        }
        UserPlan userPlan1 = new UserPlan(user, plan);
        userPlanRepository.save(userPlan1);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserPlanDTO(userPlan1));
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