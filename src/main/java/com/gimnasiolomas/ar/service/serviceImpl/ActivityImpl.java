package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.NewActivityScheduleDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.ActivitySchedule;
import com.gimnasiolomas.ar.entity.Schedule;
import com.gimnasiolomas.ar.entity.WeekDay;
import com.gimnasiolomas.ar.error.ActivityAlreadyExistException;
import com.gimnasiolomas.ar.error.NoActivityFoundException;
import com.gimnasiolomas.ar.repository.ActivityRepository;
import com.gimnasiolomas.ar.service.ActivityScheduleService;
import com.gimnasiolomas.ar.service.ActivityService;
import com.gimnasiolomas.ar.service.ScheduleService;
import com.gimnasiolomas.ar.service.UserActivityScheduleService;
import com.gimnasiolomas.ar.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ActivityScheduleService activityScheduleService;
    @Autowired
    private UserActivityScheduleService userActivityScheduleService;

    @Override
    public List<ActivityDTO> getAll() {
        return activityRepository.findAll().stream().map(ActivityDTO::new).collect(Collectors.toList());
    }
    @Override
    public ActivityDTO getActivityByName(String activityName) throws NoActivityFoundException {
        Activity activity = activityRepository.findByActivityName(activityName).orElseThrow(()-> new NoActivityFoundException(activityName + ": " + Messages.ACTIVITY_NOT_FOUND));

        return new ActivityDTO(activity);
    }
    @Override
    public List<String> listOfUsers(UsersListDTO usersListDTO) throws NoActivityFoundException {
        Activity activity = activityRepository.findByActivityName(usersListDTO.getActivityName()).orElseThrow(()-> new NoActivityFoundException(Messages.ACTIVITY_NOT_FOUND));
        return userActivityScheduleService
                .findAll()
                .stream()
                .filter(act -> act.getActivitySchedule().getActivityName().equalsIgnoreCase(usersListDTO.getActivityName()))
                .filter(act-> act.getDayHourActivity().equals(usersListDTO.getActivityClass()))
                .map(user -> user.getUser().getLastName() + ", " + user.getUser().getName())
                .collect(Collectors.toList());
    }

    @Override
    public Activity findByActivityName(String activityName)
            throws NoActivityFoundException {
        return activityRepository.findByActivityName(activityName.toLowerCase()).orElseThrow(()-> new NoActivityFoundException(activityName + ": " + Messages.ACTIVITY_NOT_FOUND));
    }

    @Override
    public ActivityDTO deleteActivity(long id)
            throws NoActivityFoundException {
        Activity activity = activityRepository.findById(id).orElseThrow(()-> new NoActivityFoundException(Messages.ACTIVITY_NOT_FOUND));
        ActivityDTO activityDTO = new ActivityDTO(activity);
        activityRepository.delete(activity);
        return activityDTO;
    }

    @Override
    public ActivityDTO getActivityById(long id) throws NoActivityFoundException {
        return new ActivityDTO(activityRepository.findById(id).orElseThrow(()-> new NoActivityFoundException(Messages.ACTIVITY_NOT_FOUND)));
    }

    @Override
    public ActivityDTO newActivity(ActivityDTO activityDTO)
            throws ActivityAlreadyExistException {

        validateActivityAlreadyExists(activityDTO);

        Activity activity = new Activity(activityDTO.getActivityName().toLowerCase(), activityDTO.getMaxMembersPerClass());
        activityRepository.save(activity);
        return new ActivityDTO(activity);
    }


    @Override
    public ActivityDTO newActivitySchedule(NewActivityScheduleDTO newActivityScheduleDTO)
            throws NoActivityFoundException {
        Activity activity = activityRepository.findByActivityName(newActivityScheduleDTO.getActivityName().toLowerCase()).orElseThrow(()-> new NoActivityFoundException(Messages.ACTIVITY_NOT_FOUND));
        WeekDay weekDay = Utility.changeToUpperCase(newActivityScheduleDTO.getWeekDay());
        Schedule schedule = new Schedule(weekDay, newActivityScheduleDTO.getHour());
        scheduleService.save(schedule);
        ActivitySchedule activitySchedule = new ActivitySchedule(activity, schedule);
        activityScheduleService.save(activitySchedule);
        return new ActivityDTO(activity);
    }

    private void validateActivityAlreadyExists(ActivityDTO activityDTO) throws ActivityAlreadyExistException {
        if(activityRepository.findByActivityName(activityDTO.getActivityName().toLowerCase()).isPresent()){
            throw new ActivityAlreadyExistException(Messages.ACTIVITY_ALREADY_EXISTS);
        }
    }
}
