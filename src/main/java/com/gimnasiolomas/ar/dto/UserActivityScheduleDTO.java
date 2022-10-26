package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.ScheduleState;
import com.gimnasiolomas.ar.entity.UserActivitySchedule;
import com.gimnasiolomas.ar.utility.Utility;

public class UserActivityScheduleDTO {
    private long id;
    private String activityName;
    private String inscription;
    private ScheduleState state;

    public UserActivityScheduleDTO(){}
    public UserActivityScheduleDTO(UserActivitySchedule userActivitySchedule){
        this.id = userActivitySchedule.getId();
        this.activityName = userActivitySchedule.getActivityName();
        this.inscription = Utility.translateDay(userActivitySchedule.getDayHourActivity().getDayOfWeek()) + ", "
                + userActivitySchedule.getDayHourActivity().getDayOfMonth() + "/"
                + userActivitySchedule.getDayHourActivity().getMonth().getValue() + "/"
                + userActivitySchedule.getDayHourActivity().getYear() + ", "
                + userActivitySchedule.getDayHourActivity().getHour() + " hs.";
        this.state = userActivitySchedule.getState();
    }

    public long getId() {
        return id;
    }
    public String getActivityName() {
        return activityName;
    }
    public String getInscription() {
        return inscription;
    }
    public ScheduleState getState() {
        return state;
    }
}
