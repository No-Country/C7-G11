package com.gimnasiolomas.ar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String activityName;
    @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER)
    private Set<ActivitySchedule> activitySchedules = new HashSet<>();
    public Activity(){}
    public Activity(String activityName){
        this.activityName = activityName;
    }

    public void addActivitySchedule(ActivitySchedule activitySchedule) {
        activitySchedule.setActivity(this);
        activitySchedules.add(activitySchedule);
    }

    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public Set<ActivitySchedule> getActivitySchedules() {
        return activitySchedules;
    }
    public void setActivitySchedules(Set<ActivitySchedule> activitySchedules) {
        this.activitySchedules = activitySchedules;
    }
}
