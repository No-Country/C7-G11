package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.PlanDTO;
import com.gimnasiolomas.ar.entity.Plan;
import com.gimnasiolomas.ar.error.PlanNotFoundException;

import java.util.Set;

public interface PlanService {
    void save(Plan plan);
    Set<PlanDTO> findAll();
    PlanDTO savePlan(PlanDTO planDTO);
    Plan getPlan(String planName)throws PlanNotFoundException;


}
