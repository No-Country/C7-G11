package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.entity.UserActivitySchedule;

import java.util.List;

public interface UserActivityScheduleService {
    void save(UserActivitySchedule userActivitySchedule);
    List<UserActivitySchedule> findAll();
}
