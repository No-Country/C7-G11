package com.gimnasiolomas.ar.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private String description;
    private double price;
    private int gymClasses;
    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER)
    private Set<UserPlan> userPlans = new HashSet<>();

    public Plan(){}

    public Plan(String name, String description, double price, int gymClases) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.gymClasses = gymClases;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void addUserPlan(UserPlan userPlan) {
        userPlan.setPlan(this);
        userPlans.add(userPlan);
    }
    public Set<UserPlan> getUserPlans() {
        return userPlans;
    }
    public void setUserPlans(Set<UserPlan> userPlans) {
        this.userPlans = userPlans;
    }
    public int getGymClases() {
        return gymClasses;
    }
    public void setGymClasses(int gymClasses) {
        this.gymClasses = gymClasses;
    }
}
