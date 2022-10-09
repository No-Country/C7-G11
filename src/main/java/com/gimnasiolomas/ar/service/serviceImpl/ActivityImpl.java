package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.ActivitySchedule;
import com.gimnasiolomas.ar.entity.Schedule;
import com.gimnasiolomas.ar.entity.WeekDay;
import com.gimnasiolomas.ar.repository.ActivityRepository;
import com.gimnasiolomas.ar.repository.ActivityScheduleRepository;
import com.gimnasiolomas.ar.repository.ScheduleRepository;
import com.gimnasiolomas.ar.service.ActivityService;
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

    @Override
    public List<ActivityDTO> getAll() {
        return activityRepository.findAll().stream().map(ActivityDTO::new).collect(Collectors.toList());
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
    public ResponseEntity<?> newActivitySchedule(String activityName, WeekDay weekDay, int hour) {
        if(activityRepository.findByActivityName(activityName)==null){
            Activity activity = new Activity(activityName);
            activityRepository.save(activity);
            Schedule schedule = new Schedule(weekDay, hour);
            scheduleRepository.save(schedule);
            ActivitySchedule activitySchedule = new ActivitySchedule(activity, schedule);
            activityScheduleRepository.save(activitySchedule);
            scheduleRepository.save(schedule);
            activityScheduleRepository.save(activitySchedule);
            activityRepository.save(activity);
            ActivityDTO activityDTO = new ActivityDTO(activityRepository.findByActivityName(activityName));

            return ResponseEntity.accepted().body(activityDTO);
        }
        Activity activity = activityRepository.findByActivityName(activityName);
        Schedule schedule = new Schedule(weekDay, hour);
        scheduleRepository.save(schedule);
        ActivitySchedule activitySchedule = new ActivitySchedule(activity, schedule);
        activityScheduleRepository.save(activitySchedule);
        scheduleRepository.save(schedule);
        activityScheduleRepository.save(activitySchedule);
        activityRepository.save(activity);
        ActivityDTO activityDTO = new ActivityDTO(activityRepository.findByActivityName(activityName));

        return ResponseEntity.accepted().body(activityDTO);
    }
}
