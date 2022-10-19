package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.UserActivitySchedule;
import com.gimnasiolomas.ar.utility.Utility;

public class UserActivityScheduleDTO {
//    private long id;
    private String activityName;
    private String algo;

    public UserActivityScheduleDTO(){}
    public UserActivityScheduleDTO(UserActivitySchedule userActivitySchedule){
//        this.id = userActivitySchedule.getId();
        this.activityName = userActivitySchedule.getActivityName();
        this.algo = Utility.translateDay(userActivitySchedule.getDayHourActivity().getDayOfWeek()) + ", "
                + userActivitySchedule.getDayHourActivity().getDayOfMonth() + "/"
                + userActivitySchedule.getDayHourActivity().getMonth().getValue() + "/"
                + userActivitySchedule.getDayHourActivity().getYear() + ", "
                + userActivitySchedule.getDayHourActivity().getHour() + " hs.";
    }

    public String getActivityName() {
        return activityName;
    }
    public String getAlgo() {
        return algo;
    }
}
