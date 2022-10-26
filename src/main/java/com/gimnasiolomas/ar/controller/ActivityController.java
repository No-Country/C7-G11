package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.NewActivityScheduleDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import com.gimnasiolomas.ar.error.ActivityAlreadyExistException;
import com.gimnasiolomas.ar.error.NoActivityFoundException;
import com.gimnasiolomas.ar.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<ActivityDTO> getAll(){
        return activityService.getAll();
    }

    @GetMapping("/{activityName}")
    public ActivityDTO getActivityByName(@PathVariable String activityName)
            throws NoActivityFoundException {
        return activityService.getActivityByName(activityName);
    }
    @GetMapping("/{id}")
    public ActivityDTO getActivityById(@PathVariable long id)
            throws NoActivityFoundException {
        return activityService.getActivityById(id);
    }
    @PostMapping
    public ResponseEntity<?> newActivity(@Valid @RequestBody ActivityDTO activityDTO)
            throws ActivityAlreadyExistException {
        return ResponseEntity.ok(activityService.newActivity(activityDTO));
    }

    @PostMapping("/new")
    public ResponseEntity<?> newActivitySchedule(@Valid @RequestBody NewActivityScheduleDTO newActivityScheduleDTO)
            throws NoActivityFoundException {
        return ResponseEntity.ok(activityService.newActivitySchedule(newActivityScheduleDTO));
    }
    @GetMapping("/userslist")
    public List<String> listOfUsers(@RequestBody UsersListDTO usersListDTO)
            throws NoActivityFoundException {
        return activityService.listOfUsers(usersListDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable long id)
            throws NoActivityFoundException {
        return ResponseEntity.ok(activityService.deleteActivity(id));
    }
}