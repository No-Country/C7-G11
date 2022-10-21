package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.dto.PlanDTO;
import com.gimnasiolomas.ar.entity.Plan;
import com.gimnasiolomas.ar.error.PlanNotFoundException;
import com.gimnasiolomas.ar.repository.PlanRepository;
import com.gimnasiolomas.ar.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlanImpl implements PlanService {
    @Autowired
    private PlanRepository planRepository;

    @Override
    public void save(Plan plan) {
        planRepository.save(plan);
    }

    @Override
    public Set<PlanDTO> findAll() {
        return planRepository.findAll().stream().map(PlanDTO::new).collect(Collectors.toSet());
    }

    @Override
    public PlanDTO savePlan(PlanDTO planDTO) {
        Plan plan = new Plan(planDTO.getName(), planDTO.getDescription(), planDTO.getPrice(), planDTO.getGymClasses());
        planRepository.save(plan);
        return new PlanDTO(plan);
    }

    @Override
    public Plan getPlan(String planName) throws PlanNotFoundException {
        return planRepository.findByName(planName).orElseThrow(() -> new PlanNotFoundException(Messages.PLAN_NOT_FOUND_EXCEPTION));
    }

}
