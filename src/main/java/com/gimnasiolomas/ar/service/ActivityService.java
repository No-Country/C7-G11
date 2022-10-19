package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> getAll();
    ResponseEntity<?> newActivity(ActivityDTO activityDTO);
    ResponseEntity<?> newActivitySchedule(String activityName, String weekDay, int hour);

    ActivityDTO getActivityByName(String activityName);

//    List<String> listOfUsers(String activityName, String weekDay, int hour);

    List<String> listOfUsers2(UsersListDTO usersListDTO);
}
