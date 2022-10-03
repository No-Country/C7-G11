package com.gimnasiolomas.ar.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private WeekDay weekDay;
    @Min(7)
    @Max(22)
    private int hour;
    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    private Set<ActivitySchedule> activitySchedules = new HashSet<>();

    public Schedule(){}
    public Schedule(WeekDay weekDay, int hour){
        this.weekDay = weekDay;
        this.hour = hour;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }
    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;

    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public void addActivitySchedule(ActivitySchedule activitySchedule) {
        activitySchedule.setSchedule(this);
        activitySchedules.add(activitySchedule);
    }

    public Set<ActivitySchedule> getActivitySchedules() {
        return activitySchedules;
    }

    public void setActivitySchedules(Set<ActivitySchedule> activitySchedules) {
        this.activitySchedules = activitySchedules;
    }
}
