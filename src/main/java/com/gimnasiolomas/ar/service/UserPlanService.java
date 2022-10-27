package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.entity.UserPlan;
import java.util.List;

public interface UserPlanService {
    void save(UserPlan userPlan);
    List<UserPlan> findAll();
}
