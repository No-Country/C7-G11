package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.dto.*;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private HolidayService holidayService;

    @Override
    public UserDTO findByEmail(Authentication authentication) {
        return new UserDTO(userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException(Messages.USER_NOT_FOUND)));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserDTO saveUser(UserRegisterDTO userRegisterDTO) throws UnderLegalAgeException {
        if (userRegisterDTO.getBirthday().isAfter(LocalDate.now().minusYears(18))) {
            throw new UnderLegalAgeException(Messages.UNDER_LEGAL_AGE_EXCEPTION);
        }
        User user1 = new User(
                userRegisterDTO.getName(),
                userRegisterDTO.getLastName(),
                userRegisterDTO.getEmail().toLowerCase(),
                passwordEncoder.encode(userRegisterDTO.getPassword()),
                userRegisterDTO.getBirthday());
        userRepository.save(user1);
        String subject = "Bienvenido a Gimnasio Lomas";
        String body = user1.getName() + " gracias por registrarte en nuestra página";
        emailSenderService.sendEmail(user1.getEmail(), subject, body);

        return new UserDTO(user1);
    }

    @Override
    public UserDTO findById(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(Messages.USER_NOT_FOUND));
        return new UserDTO(user);
    }


    @Override
    public UserActivityScheduleDTO createUserActivitySchedule(Authentication authentication,
                                                              InscriptionDTO inscriptionDTO)
            throws ActivityAlreadyScheduledException,
            NoActivityFoundException,
            NoGymClassesLeftException,
            NotFoundException,
            HolidayException, MaxNumberOfMembersReachedException {


        User user = validateLoggedUser(authentication);
        validateNotHoliday(inscriptionDTO);
        Activity activity = activityService.findByActivityName(inscriptionDTO.getActivityName());

        UserPlan userPlan = activePlan(user);
        validateGymClassesLeft(userPlan);
        validateActivityAlreadyScheduled(user, inscriptionDTO);
        validateAnyInscriptionOnSameDay(user, inscriptionDTO);
        ActivitySchedule activitySchedule = validateActivityExistOnTheDay(activity, inscriptionDTO);
        validateMaxMembersNotReached(activity, inscriptionDTO);

        UserActivitySchedule userActivitySchedule = new UserActivitySchedule(user, activitySchedule, inscriptionDTO.getDayHourActivity());
        userActivityScheduleService.save(userActivitySchedule);
        userPlan.substractGymClass();
        userPlanService.save(userPlan);
        return new UserActivityScheduleDTO(userActivitySchedule);
    }

    private void validateMaxMembersNotReached(Activity activity, InscriptionDTO inscriptionDTO) throws MaxNumberOfMembersReachedException {
        if(activity.getMaxMembersPerClass()==0){
            return;
        }
        if(userActivityScheduleService.findAll()
                .stream()
                .filter(act -> act.getActivitySchedule().getActivityName().equalsIgnoreCase(activity.getActivityName()))
                .filter(act -> act.getDayHourActivity().isEqual(inscriptionDTO.getDayHourActivity()))
                .count()>=activity.getMaxMembersPerClass()){
            throw new MaxNumberOfMembersReachedException(Messages.MAX_NUMBER_OF_MEMBERS_REACHED);
        }
    }

    @Override
    public UserPlanDTO assignNewPlan(Authentication authentication, String planName) throws PlanNotFoundException, ActivePlanException, NotFoundException {
        User user = validateLoggedUser(authentication);
        Plan plan = planService.getPlan(planName);

        validateActivePlan(user);

        UserPlan userPlan = new UserPlan(user, plan);
        welcomeEmail(user, userPlan);
        userPlanService.save(userPlan);
        return new UserPlanDTO(userPlan);
    }

    @Override
    public String deleteUser(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(Messages.USER_NOT_FOUND));
        userRepository.delete(user);
        return "Usuario eliminado";
    }

    @Override
    public UserActivityScheduleDTO cancelUserActivitySchedule(Authentication authentication,
                                                              UserActivityScheduleDTO userActivityScheduleDTO) throws NotFoundException, CancelInscriptionException {
        User user = validateLoggedUser(authentication);
        UserActivitySchedule userActivitySchedule = userActivityScheduleService.findByID(userActivityScheduleDTO.getId());

        validateCancelableInscription(user, userActivitySchedule);

        userActivitySchedule.setState(ScheduleState.CANCELADO);
        userActivityScheduleService.save(userActivitySchedule);
        return new UserActivityScheduleDTO(userActivitySchedule);
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


    private void validateCancelableInscription(User user, UserActivitySchedule userActivitySchedule) throws NotFoundException, CancelInscriptionException {
        if(!user.getUserActivitySchedules().contains(userActivitySchedule)){
            throw new NotFoundException(Messages.NO_INSCRIPTION);
        }
        if(LocalDateTime.now().isAfter(userActivitySchedule.getDayHourActivity().minusHours(1))){
            throw new CancelInscriptionException(Messages.CANCEL_OUT_OF_RANGE);
        }
        if(userActivitySchedule.getState()==ScheduleState.CANCELADO){
            throw new CancelInscriptionException(Messages.INSCRIPTION_ALREADY_CANCELED);
        }
        if(userActivitySchedule.getState()==ScheduleState.ASISTIDO){
            throw new CancelInscriptionException(Messages.INSCRIPTION_ALREADY_ASSISTED);
        }
    }
    private User validateLoggedUser(Authentication authentication) throws NotFoundException {
        if (authentication == null) {
            throw new NotFoundException(Messages.USER_NOT_LOGGED);
        }
        return userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NotFoundException(Messages.USER_NOT_FOUND));
    }
    private void validateNotHoliday(InscriptionDTO inscriptionDTO) throws HolidayException {
        if(inscriptionDTO.getDayHourActivity().getDayOfWeek() == DayOfWeek.SUNDAY){
            throw new HolidayException(Messages.SUNDAY_EXCEPTION);
        }
        if(holidayService.findAll()
                .stream()
                .anyMatch(el-> el.getDate().isEqual(inscriptionDTO.getDayHourActivity().toLocalDate()))){
            throw new HolidayException(Messages.HOLIDAY_EXCEPTION);
        }
    }
    private ActivitySchedule validateActivityExistOnTheDay(Activity activity, InscriptionDTO inscriptionDTO) throws NoActivityFoundException {
        return activity.getActivitySchedules()
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
    }
    private void validateAnyInscriptionOnSameDay(User user, InscriptionDTO inscriptionDTO) throws ActivityAlreadyScheduledException {
        for (UserActivitySchedule uas : user.getUserActivitySchedules().stream().filter(act -> act.getState()==ScheduleState.INSCRIPTO).collect(Collectors.toList())){
            if(uas.getDayHourActivity().getYear()==inscriptionDTO.getDayHourActivity().getYear()
                    && uas.getDayHourActivity().getMonth()==inscriptionDTO.getDayHourActivity().getMonth()
                    && uas.getDayHourActivity().getDayOfMonth()==inscriptionDTO.getDayHourActivity().getDayOfMonth()){
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
    }
    private void validateGymClassesLeft(UserPlan userPlan) throws NoGymClassesLeftException {
        if(userPlan.getGymClassesLeft()==0){
            throw new NoGymClassesLeftException(Messages.NO_GYM_CLASSES_LEFT);
        }
    }
    private void validateActivityAlreadyScheduled(User user, InscriptionDTO inscriptionDTO) throws ActivityAlreadyScheduledException {
        if(user.getUserActivitySchedules()
                .stream()
                .filter(act -> act.getActivityName().equalsIgnoreCase(inscriptionDTO.getActivityName()))
                .filter(act -> act.getState()==ScheduleState.INSCRIPTO)
                .anyMatch(act -> act.getDayHourActivity().isEqual(inscriptionDTO.getDayHourActivity()))){

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
    }
    private void validateActivePlan(User user) throws ActivePlanException {
        if (user.getUserPlans().stream().anyMatch(UserPlan::isActive)){
            throw new ActivePlanException(Messages.ACTIVE_PLAN_EXCEPTION);
        }
    }
    private UserPlan activePlan(User user) throws NotFoundException {
        return user.getUserPlans().stream().filter(UserPlan::isActive).findFirst().orElseThrow(()-> new NotFoundException(Messages.MEMBERSHIP_NOT_FOUND));
    }
    private void welcomeEmail(User user, UserPlan userPlan){
        if(user.getUserPlans().size()==0) {
            String body = "Estimado/a " + user.getName() + ":"
                    + "\nTenemos el agrado de darte la bienvenida como nuevo miembro del Gimnasio Lomas."
                    + "\nTu número de socio es: " + userMembershipNumber(userPlan)
                    + "\nTu membresía está vigente desde el " + userPlan.getStartDate() + " hasta el " + userPlan.getExpireDate()
                    + "\nAgradecemos tu confianza y esperamos que disfrutes de nuestros servicios.\n"
                    + "\nSaludos!\n"
                    + "\nGimnasio Lomas.";
            emailSenderService.sendEmail(user.getEmail(), Messages.WELCOME, body);
        }
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
}