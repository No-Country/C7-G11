package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.entity.UserPlan;
import com.gimnasiolomas.ar.repository.UserPlanRepository;
import com.gimnasiolomas.ar.service.UserPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserPlanImpl implements UserPlanService {
    @Autowired
    private UserPlanRepository userPlanRepository;
    @Override
    public void save(UserPlan userPlan) {
        userPlanRepository.save(userPlan);
    }
    @Override
    public List<UserPlan> findAll(){
        return userPlanRepository.findAll();
    }
}