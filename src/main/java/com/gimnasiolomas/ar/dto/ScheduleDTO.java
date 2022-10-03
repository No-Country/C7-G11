package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.Schedule;
import com.gimnasiolomas.ar.entity.WeekDay;

public class ScheduleDTO {
    private long id;
    private WeekDay weekDay;
    private int hour;

    public ScheduleDTO() {
    }

    public ScheduleDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.weekDay = schedule.getWeekDay();
        this.hour = schedule.getHour();
    }

    public long getId() {
        return id;
    }
    public WeekDay getWeekDay() {
        return weekDay;
    }
    public int getHour() {
        return hour;
    }
}
