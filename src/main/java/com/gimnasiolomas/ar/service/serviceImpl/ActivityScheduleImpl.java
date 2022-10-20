package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.entity.ActivitySchedule;
import com.gimnasiolomas.ar.repository.ActivityScheduleRepository;
import com.gimnasiolomas.ar.service.ActivityScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityScheduleImpl implements ActivityScheduleService {
    @Autowired
    private ActivityScheduleRepository activityScheduleRepository;
    @Override
    public void save(ActivitySchedule activitySchedule) {
        activityScheduleRepository.save(activitySchedule);
    }
}
