package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.entity.Schedule;
import com.gimnasiolomas.ar.repository.ScheduleRepository;
import com.gimnasiolomas.ar.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
}
