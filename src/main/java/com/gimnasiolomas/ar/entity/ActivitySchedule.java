package com.gimnasiolomas.ar.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ActivitySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserActivitySchedule> userActivitySchedules = new HashSet<>();
    private String activityName;

    public ActivitySchedule(){}
    public ActivitySchedule(Activity activity, Schedule schedule){
        this.activity = activity;
        this.schedule = schedule;
        this.activityName = activity.getActivityName();
    }

    public long getId() {
        return id;
    }
    @JsonIgnore
    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    @JsonIgnore
    public Activity getActivity() {
        return activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public void addUserActivity(UserActivitySchedule userActivitySchedule) {
        userActivitySchedule.setActivitySchedule(this);
        userActivitySchedules.add(userActivitySchedule);
    }
    public Set<UserActivitySchedule> getUserActivitySchedules() {
        return userActivitySchedules;
    }
    public void setUserActivitySchedules(Set<UserActivitySchedule> userActivitySchedules) {
        this.userActivitySchedules = userActivitySchedules;
    }
}
