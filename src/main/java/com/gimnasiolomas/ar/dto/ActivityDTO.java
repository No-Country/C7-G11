package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.WeekDay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActivityDTO {
    private long id;
    private String activity;
    private Map<WeekDay, Set<Integer>> horarios = new HashMap<>();

    public ActivityDTO(){}

    public ActivityDTO(Activity activity){
        this.id = activity.getId();
        this.activity = activity.getActivityName();
        this.groupHours(activity);
    }


    public long getId() {
        return id;
    }
    public String getActivity() {
        return activity;
    }


    public void groupHours(Activity activity){
        activity.getActivitySchedules().forEach(act->{
            if (this.horarios.containsKey(act.getSchedule().getWeekDay())) {
                this.horarios.get(act.getSchedule().getWeekDay()).add(act.getSchedule().getHour());

            } else {
                Set<Integer> auxSet = new HashSet<>();
                auxSet.add(act.getSchedule().getHour());
                this.horarios.put(act.getSchedule().getWeekDay(), auxSet);
            }

        });
    }

    public Map<WeekDay, Set<Integer>> getHorarios() {
        return horarios;
    }
}
