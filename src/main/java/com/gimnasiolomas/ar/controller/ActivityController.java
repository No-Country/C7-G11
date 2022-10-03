package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.ActivityDTO;
import com.gimnasiolomas.ar.entity.WeekDay;
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

    @PostMapping
    public ResponseEntity<?> newActivity(@RequestBody ActivityDTO activityDTO){
        return activityService.newActivity(activityDTO);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newActivitySchedule(@Validated @RequestParam String activityName,
                                                 @RequestParam WeekDay weekDay,
                                                 @RequestParam int hour){
        return activityService.newActivitySchedule(activityName, weekDay, hour);
    }
}