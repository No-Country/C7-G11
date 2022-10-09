package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.UserActivitySchedule;
import com.gimnasiolomas.ar.entity.WeekDay;

public class UserActivityScheduleDTO {
    private long id;
    private String activityName;
    private WeekDay day;
    private int hour;

    public UserActivityScheduleDTO(){}
    public UserActivityScheduleDTO(UserActivitySchedule userActivitySchedule){
        this.id = userActivitySchedule.getId();
        this.activityName = userActivitySchedule.getActivityName();
        this.day = userActivitySchedule.getDay();
        this.hour = userActivitySchedule.getHour();
    }

    public long getId() {
        return id;
    }
    public String getActivityName() {
        return activityName;
    }
    public WeekDay getDay() {
        return day;
    }
    public int getHour() {
        return hour;
    }
}
