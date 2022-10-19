package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.UserPlan;

import java.time.LocalDate;

public class UserPlanDTO {
    private long id;
    private String userName;
    private String planName;
    private LocalDate startDate;
    private LocalDate expireDate;
    private int gymClassesLeft;
    private boolean isActive;

    public UserPlanDTO(){}
    public UserPlanDTO(UserPlan userPlan){
        this.id = userPlan.getId();
        this.userName = userPlan.getUser().getLastName() + ", " + userPlan.getUser().getName();
        this.planName = userPlan.getPlan().getName();
        this.startDate = userPlan.getStartDate();
        this.expireDate = userPlan.getExpireDate();
        this.gymClassesLeft = userPlan.getGymClassesLeft();
        this.isActive = userPlan.isActive();
    }

    public long getId() {
        return id;
    }
    public String getUserName() {
        return userName;
    }
    public String getPlanName() {
        return planName;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getExpireDate() {
        return expireDate;
    }
    public int getGymClassesLeft() {
        return gymClassesLeft;
    }
    public boolean isActive() {
        return isActive;
    }
}
