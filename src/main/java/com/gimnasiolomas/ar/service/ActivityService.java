package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.NewActivityScheduleDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.error.ActivityAlreadyExistException;
import com.gimnasiolomas.ar.error.NoActivityFoundException;

import java.util.List;

public interface ActivityService {
    List<ActivityDTO> getAll();
    ActivityDTO newActivity(ActivityDTO activityDTO) throws ActivityAlreadyExistException;
    ActivityDTO newActivitySchedule(NewActivityScheduleDTO newActivityScheduleDTO) throws NoActivityFoundException;
    ActivityDTO getActivityByName(String activityName) throws NoActivityFoundException;
    List<String> listOfUsers(UsersListDTO usersListDTO) throws NoActivityFoundException;
    Activity findByActivityName(String activityName) throws NoActivityFoundException;
    ActivityDTO deleteActivity(long id) throws NoActivityFoundException;
    ActivityDTO getActivityById(long id) throws NoActivityFoundException;
}
