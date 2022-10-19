package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.PlanDTO;
import com.gimnasiolomas.ar.entity.Plan;
import com.gimnasiolomas.ar.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface PlanService {
    Set<PlanDTO> findAll();
    ResponseEntity<?> savePlan(PlanDTO planDTO);
}
