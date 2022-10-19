package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.ActivitySchedule;
import com.gimnasiolomas.ar.entity.Schedule;
import com.gimnasiolomas.ar.entity.WeekDay;
import com.gimnasiolomas.ar.error.NotFoundException;
import com.gimnasiolomas.ar.repository.*;
import com.gimnasiolomas.ar.service.ActivityService;
import com.gimnasiolomas.ar.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ActivityScheduleRepository activityScheduleRepository;
    @Autowired
    private UserActivityScheduleRepository userActivityScheduleRepository;

    @Override
    public List<ActivityDTO> getAll() {
        return activityRepository.findAll().stream().map(ActivityDTO::new).collect(Collectors.toList());
    }
    @Override
    public ActivityDTO getActivityByName(String activityName) {
        if(activityRepository.findByActivityName(activityName)==null){
            throw new NotFoundException("Actividad: " + activityName + ", no encontrada");
        }
        return new ActivityDTO(activityRepository.findByActivityName(activityName));
    }

//    @Override
//    public List<String> listOfUsers(String activityName, String weekDay, int hour) {
//        WeekDay weekDay1 = Utility.changeToUpperCase(weekDay);
//        return userActivityScheduleRepository
//                .findAll()
//                .stream()
//                .filter(act -> act.getActivitySchedule().getActivityName().equalsIgnoreCase(activityName))
//                .filter(act -> act.getActivitySchedule().getSchedule().getWeekDay().equals(weekDay1))
//                .filter(act-> act.getActivitySchedule().getSchedule().getHour()==hour)
//                .map(user -> user.getUser().getLastName() + ", " + user.getUser().getName())
//                .collect(Collectors.toList());
//    }

    @Override
    public List<String> listOfUsers2(UsersListDTO usersListDTO) {
        return userActivityScheduleRepository
                .findAll()
                .stream()
                .filter(act -> act.getActivitySchedule().getActivityName().equalsIgnoreCase(usersListDTO.getActivityName()))
                .filter(act-> act.getDayHourActivity().equals(usersListDTO.getActivityClass()))
                .map(user -> user.getUser().getLastName() + ", " + user.getUser().getName())
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> newActivity(ActivityDTO activityDTO) {
        if(activityRepository.findByActivityName(activityDTO.getActivity())!=null){
            return ResponseEntity.accepted().body("Activity already exists");
        }
        Activity activity = new Activity(activityDTO.getActivity());
        activityRepository.save(activity);

        return ResponseEntity.accepted().body(new ActivityDTO(activity));
    }

    @Override
    public ResponseEntity<?> newActivitySchedule(String activityName, String weekDay, int hour) {
        if(activityRepository.findByActivityName(activityName)==null){
            Activity activity = new Activity(activityName);
            activityRepository.save(activity);
            WeekDay weekDay1 = Utility.changeToUpperCase(weekDay);
            Schedule schedule = new Schedule(weekDay1, hour);
            scheduleRepository.save(schedule);
            ActivitySchedule activitySchedule = new ActivitySchedule(activity, schedule);
            activityScheduleRepository.save(activitySchedule);
            ActivityDTO activityDTO = new ActivityDTO(activityRepository.findByActivityName(activityName));
            return ResponseEntity.accepted().body(activityDTO);
        }
        Activity activity = activityRepository.findByActivityName(activityName);
        WeekDay weekDay1 = Utility.changeToUpperCase(weekDay);
        Schedule schedule = new Schedule(weekDay1, hour);
        scheduleRepository.save(schedule);
        ActivitySchedule activitySchedule = new ActivitySchedule(activity, schedule);
        activityScheduleRepository.save(activitySchedule);
        ActivityDTO activityDTO = new ActivityDTO(activityRepository.findByActivityName(activityName));
        return ResponseEntity.accepted().body(activityDTO);
    }
}
