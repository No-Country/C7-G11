package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.Plan;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PlanDTO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @PositiveOrZero
    private double price;
    @PositiveOrZero
    private int gymClasses;
    private Set<UserPlanDTO> userPlansDTO = new HashSet<>();

    public PlanDTO(){}
    public PlanDTO(Plan plan){
        this.id = plan.getId();
        this.name = plan.getName();
        this.description = plan.getDescription();
        this.price = plan.getPrice();
        this.gymClasses = plan.getGymClases();
        this.userPlansDTO = plan.getUserPlans().stream().map(UserPlanDTO::new).collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public Set<UserPlanDTO> getUserPlansDTO() {
        return userPlansDTO;
    }
    public int getGymClasses() {
        return gymClasses;
    }
}
