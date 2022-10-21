package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.dto.InscriptionDTO;
import com.gimnasiolomas.ar.dto.UserActivityScheduleDTO;
import com.gimnasiolomas.ar.dto.UserDTO;
import com.gimnasiolomas.ar.dto.UserPlanDTO;
import com.gimnasiolomas.ar.entity.*;
import com.gimnasiolomas.ar.error.*;
import com.gimnasiolomas.ar.repository.*;
import com.gimnasiolomas.ar.service.*;
import com.gimnasiolomas.ar.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new UserDTO(userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND)));
    }

    @Override
    public List<UserDTO> findAll() {
//        System.out.println(userRepository.findAll().stream().map(User::getName).collect(Collectors.toList()));
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserDTO saveUser(User user) throws UnderLegalAgeException {
        if (user.getBirthday().isAfter(LocalDate.now().minusYears(18))) {
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
        String body = user1.getName() + " gracias por registrarte en nuestra página";
        emailSenderService.sendEmail(user1.getEmail(), subject, body);

        return new UserDTO(user1);
    }

    @Override
    public UserDTO findById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return new UserDTO(user);
    }

    @Override
    public UserActivityScheduleDTO createUserActivitySchedule(Authentication authentication,
                                                              InscriptionDTO inscriptionDTO) throws ActivityAlreadyScheduledException, NoActivityFoundException, NoGymClassesLeftException {

        if (authentication == null) {
            throw new NotFoundException(Messages.USER_NOT_LOGGED);
        }
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NotFoundException(Messages.USER_NOT_FOUND));

        Activity activity = activityService.findByActivityName(inscriptionDTO.getActivityName());
        if (activity == null) {
            throw new NotFoundException(Messages.ACTIVITY_NOT_FOUND);
        }
        if (!validateActivePlan(user)) {
            throw new NotFoundException(Messages.MEMBERSHIP_NOT_FOUND);
        }

        UserPlan userPlan = user.getUserPlans().stream().filter(UserPlan::isActive).findFirst().orElseThrow(() -> new ActivePlanException(Messages.MEMBERSHIP_NOT_FOUND));
        if (userPlan.getGymClassesLeft() == 0) {
            throw new NoGymClassesLeftException(Messages.NO_GYM_CLASSES_LEFT);
        }

        if (validateActivityAlreadyScheduled(user, inscriptionDTO)) {
            throw new ActivityAlreadyScheduledException("Ya estabas inscripto para la clase de "
                    + inscriptionDTO.getActivityName()
                    + " el dia "
                    + Utility.translateDay(inscriptionDTO.getDayHourActivity().getDayOfWeek())
                    + " "
                    + inscriptionDTO.getDayHourActivity().getDayOfMonth()
                    + "/"
                    + inscriptionDTO.getDayHourActivity().getMonth().getValue()
                    + " a las "
                    + inscriptionDTO.getDayHourActivity().getHour()
                    + " hs"
            );
        }

        for (UserActivitySchedule uas : user.getUserActivitySchedules()) {
            if (uas.getDayHourActivity().getMonth() == inscriptionDTO.getDayHourActivity().getMonth()
                    && uas.getDayHourActivity().getDayOfMonth() == inscriptionDTO.getDayHourActivity().getDayOfMonth()
                    && uas.getDayHourActivity().getYear() == inscriptionDTO.getDayHourActivity().getYear()) {
                throw new ActivityAlreadyScheduledException("No te puedes inscribir a más de 1 clase por día. Estabas inscripto a la clase de "
                        + uas.getActivityName()
                        + ", el día "
                        + Utility.translateDay(inscriptionDTO.getDayHourActivity().getDayOfWeek())
                        + " "
                        + uas.getDayHourActivity().getDayOfMonth()
                        + "/"
                        + uas.getDayHourActivity().getMonth().getValue()
                        + ", a las "
                        + uas.getDayHourActivity().getHour()
                        + " hs."
                );
            }
        }

        ActivitySchedule activitySchedule = activity.getActivitySchedules()
                .stream()
                .filter(act -> act.getSchedule().getWeekDay().equals(Utility.translateDay(inscriptionDTO.getDayHourActivity().getDayOfWeek())))
                .filter(act -> act.getSchedule().getHour() == inscriptionDTO.getDayHourActivity().getHour())
                .findAny()
                .orElseThrow(() -> new NoActivityFoundException("No existe la actividad "
                        + inscriptionDTO.getActivityName()
                        + " en el horario: "
                        + inscriptionDTO.getDayHourActivity().getHour()
                        + " hs. el día "
                        + Utility.translateDay(inscriptionDTO.getDayHourActivity().getDayOfWeek())
                ));

        UserActivitySchedule userActivitySchedule = new UserActivitySchedule(user, activitySchedule, inscriptionDTO.getDayHourActivity());
        userActivityScheduleService.save(userActivitySchedule);
        userPlan.substractGymClass();
        userPlanService.save(userPlan);
        return new UserActivityScheduleDTO(userActivitySchedule);
    }

    private boolean validateActivityAlreadyScheduled(User user, InscriptionDTO inscriptionDTO) {
        return user.getUserActivitySchedules()
                .stream()
                .filter(act -> act.getActivityName().equalsIgnoreCase(inscriptionDTO.getActivityName()))
                .filter(act -> act.getDayHourActivity().getYear() == inscriptionDTO.getDayHourActivity().getYear())
                .filter(act -> act.getDayHourActivity().getMonth().getValue() == inscriptionDTO.getDayHourActivity().getMonth().getValue())
                .filter(act -> act.getDayHourActivity().getDayOfMonth() == inscriptionDTO.getDayHourActivity().getDayOfMonth())
                .anyMatch(act -> act.getDayHourActivity().getHour() == inscriptionDTO.getDayHourActivity().getHour());
    }

    @Override
    public UserPlanDTO assignNewPlan(Authentication authentication, String planName) throws PlanNotFoundException {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NotFoundException(Messages.USER_NOT_FOUND));
        Plan plan = planService.getPlan(planName);

        if (validateActivePlan(user)) {
            throw new ActivePlanException(Messages.ACTIVE_PLAN_EXCEPTION);
        }
        UserPlan userPlan = new UserPlan(user, plan);
        if(user.getUserPlans().size()==0){
            welcomeEmail(user, userPlan);
        }
        userPlanService.save(userPlan);
        return new UserPlanDTO(userPlan);
    }

    @Override
    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(Messages.USER_NOT_FOUND));
        userRepository.delete(user);
        return "Usuario eliminado";
    }

    private boolean validateActivePlan(User user) {
        return user.getUserPlans().stream().anyMatch(UserPlan::isActive);
    }
    private void welcomeEmail(User user, UserPlan userPlan){
        String body = "Estimado/a " + user.getName() + ":"
                + "\nTenemos el agrado de darte la bienvenida como nuevo miembro del Gimnasio Lomas."
                + "\nTu número de socio es: " + userMembershipNumber(userPlan)
                + "\nTu membresía está vigente desde el " +  userPlan.getStartDate() + " hasta el " + userPlan.getExpireDate()
                + "\nAgradecemos tu confianza y esperamos que disfrutes de nuestros servicios.\n"
                + "\nSaludos!\n"
                + "\nGimnasio Lomas.";
        emailSenderService.sendEmail(user.getEmail(), Messages.WELCOME, body);
    }

    private static String userMembershipNumber(UserPlan userPlan) {
        String planLetter = "";
        if(userPlan.getPlan().getName().equalsIgnoreCase("vip")){
            planLetter+= "V";
        } else if (userPlan.getPlan().getName().equalsIgnoreCase("Premium")) {
            planLetter+= "P";
        } else if (userPlan.getPlan().getName().equalsIgnoreCase("basic")) {
            planLetter+="B";
        }
        StringBuilder planNumber = new StringBuilder("" + userPlan.getUser().getId());
        while (planNumber.length()<7){
            planNumber.insert(0, "0");
        }
        return planLetter + planNumber;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPassword) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrPassword(emailOrPassword, emailOrPassword);
        if (user == null) {
            throw new UsernameNotFoundException(Messages.USER_NOTFOUND);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getName()));
        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), authorities);
    }
}