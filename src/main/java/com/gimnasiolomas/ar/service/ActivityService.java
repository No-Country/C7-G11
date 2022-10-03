package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.entity.WeekDay;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> getAll();
    ResponseEntity<?> newActivity(ActivityDTO activityDTO);
    ResponseEntity<?> newActivitySchedule(String activityName, WeekDay weekDay, int hour);

}
