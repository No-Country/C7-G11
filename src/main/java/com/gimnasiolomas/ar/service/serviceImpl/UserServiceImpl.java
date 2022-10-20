package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.dto.UserPlanDTO;
import com.gimnasiolomas.ar.entity.*;
import com.gimnasiolomas.ar.error.ActivePlanException;
import com.gimnasiolomas.ar.error.NotFoundException;
import com.gimnasiolomas.ar.error.PlanNotFoundException;
import com.gimnasiolomas.ar.error.UnderLegalAgeException;
import com.gimnasiolomas.ar.repository.*;
import com.gimnasiolomas.ar.service.*;
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

import java.time.LocalDate;
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
    private ActivityService activityService;
    @Autowired
    private UserActivityScheduleService userActivityScheduleService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private UserPlanService userPlanService;
    @Autowired
    private PlanService planService;


    @Override
    public UserDTO findByEmail(Authentication authentication) {
        return new UserDTO(userRepository.findByEmail(authentication.getName()));
    }
    @Override
    public List<UserDTO> findAll() {
//        System.out.println(userRepository.findAll().stream().map(User::getName).collect(Collectors.toList()));
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }
    @Override
    public UserDTO saveUser(User user) throws UnderLegalAgeException {
        if(user.getBirthday().isAfter(LocalDate.now().minusYears(18))){
            throw new UnderLegalAgeException(Messages.UNDER_LEGAL_AGE_EXCEPTION);
        }
        User user1 = new User(
                user.getName(),
                user.getLastName(),
                user.getEmail().toLowerCase(),
                passwordEncoder.encode(user.getPassword()),
                user.getBirthday());
        userRepository.save(user1);
        String subject = "Bienvenido a Gimnasio Lomas";
        String body = "Gracias por registrarte en nuestra página, bla bla bla";
        emailSenderService.sendEmail(user1.getEmail(), subject, body);

        return new UserDTO(user1);
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

        Activity activity = activityService.findByActivityName(inscriptionDTO.getActivityName());
        if(activity == null){
            throw new NotFoundException("Actividad no encontrada");
        }

        UserPlan userPlan = user.getUserPlans().stream().filter(UserPlan::isActive).findFirst().orElse(null);
        if(userPlan==null){
            throw new NotFoundException("Membresía vencída");
        }
        if(userPlan.getGymClassesLeft()==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Messages.NO_GYM_CLASSES_LEFT);
        }

        WeekDay weekDay1 = Utility.translateDay(inscriptionDTO.getDayHourActivity().getDayOfWeek());
        for(UserActivitySchedule uas : user.getUserActivitySchedules()){
            if(uas.getActivitySchedule().getActivity().getActivityName().equalsIgnoreCase(inscriptionDTO.getActivityName())
                    && uas.getActivitySchedule().getSchedule().getHour()== inscriptionDTO.getDayHourActivity().getHour()
                    && uas.getActivitySchedule().getSchedule().getWeekDay()==weekDay1){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Ya estabas inscripto para la clase de "
                                + activity.getActivityName()
                                + " el dia "
                                + weekDay1
                                + " "
                                + uas.getDayHourActivity().getDayOfMonth()
                                + "/"
                                + uas.getDayHourActivity().getMonth().getValue()
                                + " a las "
                                + inscriptionDTO.getDayHourActivity().getHour()
                                + " hs");
            }
        }

        for(UserActivitySchedule uas : user.getUserActivitySchedules()){
            if(uas.getDayHourActivity().getMonth() == inscriptionDTO.getDayHourActivity().getMonth()
                    && uas.getDayHourActivity().getDayOfMonth() == inscriptionDTO.getDayHourActivity().getDayOfMonth()
                    && uas.getDayHourActivity().getYear() == inscriptionDTO.getDayHourActivity().getYear()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("No te puedes inscribir a más de 1 clase por día. Estabas inscripto a la clase de "
                                + uas.getActivityName()
                                + ", el día "
                                + weekDay1
                                + " "
                                +uas.getDayHourActivity().getDayOfMonth()
                                + "/"
                                + uas.getDayHourActivity().getMonth().getValue()
                                + ", a las "
                                + uas.getDayHourActivity().getHour()
                                + " hs.");
            }
        }

        for(ActivitySchedule sch : activity.getActivitySchedules()){
            if(sch.getSchedule().getHour()== inscriptionDTO.getDayHourActivity().getHour() && sch.getSchedule().getWeekDay()==weekDay1){
                UserActivitySchedule userActivitySchedule = new UserActivitySchedule(user, sch, inscriptionDTO.getDayHourActivity());
                userActivityScheduleService.save(userActivitySchedule);
                userPlan.substractGymClass();
                userPlanService.save(userPlan);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Inscripto a la clase de "
                                + activity.getActivityName()
                                + ", el día: "
                                + userActivitySchedule.getActivitySchedule().getSchedule().getWeekDay()
                                + ", a las "
                                + userActivitySchedule.getActivitySchedule().getSchedule().getHour()
                                + " hs.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe la actividad " + inscriptionDTO.getActivityName() + " en el horario: " + inscriptionDTO.getDayHourActivity().getHour() + " hs. el día " + weekDay1);
    }

    @Override
    public UserPlanDTO assignNewPlan(Authentication authentication, String planName) throws PlanNotFoundException {
         User user = userRepository.findByEmail(authentication.getName());
         Plan plan = planService.getPlan(planName);

        if(validateActivePlan(user)){
              throw new ActivePlanException(Messages.ACTIVE_PLAN_EXCEPTION);
        }
        UserPlan userPlan = new UserPlan(user, plan);
        userPlanService.save(userPlan);
        return new UserPlanDTO(userPlan);
    }



    private boolean validateActivePlan(User user){
        return user.getUserPlans().stream().anyMatch(UserPlan::isActive);
    }


    @Override
    public UserDetails loadUserByUsername(String emailOrPassword) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPassword(emailOrPassword, emailOrPassword);

        if(user == null){
            throw new UsernameNotFoundException(Messages.USER_NOTFOUND);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getName()));

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), authorities);
    }
}