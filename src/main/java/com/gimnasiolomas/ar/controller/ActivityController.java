package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.dto.UsersListDTO;
import com.gimnasiolomas.ar.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ActivityDTO getActivityByName(@PathVariable String activityName){
        return activityService.getActivityByName(activityName);
    }
    @PostMapping
    public ResponseEntity<?> newActivity(@RequestBody ActivityDTO activityDTO){
        return activityService.newActivity(activityDTO);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newActivitySchedule(@Validated @RequestParam String activityName,
                                                 @RequestParam String weekDay,
                                                 @RequestParam int hour){
        return activityService.newActivitySchedule(activityName, weekDay, hour);
    }
//    @GetMapping("/{activityName}/{weekDay}/{hour}")
//    public List<String> listOfUsers(@PathVariable String activityName,
//                                     @PathVariable String weekDay,
//                                     @PathVariable int hour){
//        return activityService.listOfUsers(activityName, weekDay, hour);
//    }
    @GetMapping("/userslist")
    public List<String> listOfUsers2(@RequestBody UsersListDTO usersListDTO){
        return activityService.listOfUsers2(usersListDTO);
    }

}