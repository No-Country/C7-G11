package com.gimnasiolomas.ar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class UserPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDate startDate;
    private LocalDate expireDate;
    private int gymClassesLeft;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public UserPlan(){}
    public UserPlan(User user, Plan plan){
        this.startDate = LocalDate.now();
        this.expireDate = LocalDate.now().plusMonths(1);
        this.user = user;
        this.plan = plan;
        this.gymClassesLeft = plan.getGymClases();
        this.isActive = true;
    }

    public long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Plan getPlan() {
        return plan;
    }
    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
    public int getGymClassesLeft() {
        return gymClassesLeft;
    }
    public void setGymClassesLeft(int gymClassesLeft) {
        this.gymClassesLeft = gymClassesLeft;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public void substractGymClass(){
        if(this.gymClassesLeft>0){
        this.gymClassesLeft--;
        }
    }
    public void addGymClass(){
            this.gymClassesLeft++;
    }
}
