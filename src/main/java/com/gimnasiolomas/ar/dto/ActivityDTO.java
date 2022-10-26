package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.WeekDay;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActivityDTO {
    private long id;
    @NotBlank
    private String activityName;
    private Map<WeekDay, Set<Integer>> horarios = new HashMap<>();
    @PositiveOrZero(message = "Debe ser mayor o igual a 0, en caso de que sea 0, no hay límite de miembros")
    private int maxMembersPerClass;

    public ActivityDTO(){}

    public ActivityDTO(Activity activity){
        this.id = activity.getId();
        this.activityName = activity.getActivityName();
        this.groupHours(activity);
        this.maxMembersPerClass = activity.getMaxMembersPerClass();
    }


    public long getId() {
        return id;
    }
    public String getActivityName() {
        return activityName;
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

    public int getMaxMembersPerClass() {
        return maxMembersPerClass;
    }

    public Map<WeekDay, Set<Integer>> getHorarios() {
        return horarios;
    }
}
