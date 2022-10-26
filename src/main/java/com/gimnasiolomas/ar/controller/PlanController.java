package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.PlanDTO;
import com.gimnasiolomas.ar.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    @GetMapping
    public Set<PlanDTO> findAllPlans(){
        return planService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> savePlan(@Valid @RequestBody PlanDTO planDTO){
        return ResponseEntity.ok(planService.savePlan(planDTO));
    }
}
