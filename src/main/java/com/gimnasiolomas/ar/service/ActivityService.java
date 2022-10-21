package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.error.ActivityAlreadyExistException;
import com.gimnasiolomas.ar.error.NoActivityFoundException;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> getAll();
    ActivityDTO newActivity(ActivityDTO activityDTO) throws ActivityAlreadyExistException;
    ActivityDTO newActivitySchedule(String activityName, String weekDay, int hour);
    ActivityDTO getActivityByName(String activityName) throws NoActivityFoundException;
    List<String> listOfUsers(UsersListDTO usersListDTO) throws NoActivityFoundException;
    Activity findByActivityName(String activityName) throws NoActivityFoundException;
    String deleteActivity(long id) throws NoActivityFoundException;
}
