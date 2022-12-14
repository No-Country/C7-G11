package com.gimnasiolomas.ar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserActivitySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activitySchedule_id")
    private ActivitySchedule activitySchedule;
    private String activityName;
    private WeekDay day;
    private int hour;
    private LocalDateTime dayHourActivity;
    private ScheduleState state;


    public UserActivitySchedule(){}
    public UserActivitySchedule(User user, ActivitySchedule activitySchedule, LocalDateTime dayHourActivity){
        this.user = user;
        this.activitySchedule = activitySchedule;
        this.activityName = activitySchedule.getActivityName();
        this.day = activitySchedule.getSchedule().getWeekDay();
        this.hour = activitySchedule.getSchedule().getHour();
        this.dayHourActivity = dayHourActivity;
        this.state = ScheduleState.INSCRIPTO;
    }

    public long getId() {
        return id;
    }
//    @JsonIgnore
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public ActivitySchedule getActivitySchedule() {
        return activitySchedule;
    }
    public void setActivitySchedule(ActivitySchedule activitySchedule) {
        this.activitySchedule = activitySchedule;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public WeekDay getDay() {
        return day;
    }
    public void setDay(WeekDay day) {
        this.day = day;
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public LocalDateTime getDayHourActivity() {
        return dayHourActivity;
    }
    public void setDayHourActivity(LocalDateTime dayHourActivity) {
        this.dayHourActivity = dayHourActivity;
    }
    public ScheduleState getState() {
        return state;
    }
    public void setState(ScheduleState state) {
        this.state = state;
    }
}