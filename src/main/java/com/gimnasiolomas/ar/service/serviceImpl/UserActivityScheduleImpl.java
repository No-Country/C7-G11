package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.entity.UserActivitySchedule;
import com.gimnasiolomas.ar.repository.UserActivityScheduleRepository;
import com.gimnasiolomas.ar.service.UserActivityScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import java.util.List;

@Service
public class UserActivityScheduleImpl implements UserActivityScheduleService {
    @Autowired
    private UserActivityScheduleRepository userActivityScheduleRepository;
    @Override
    public void save(UserActivitySchedule userActivitySchedule) {
        userActivityScheduleRepository.save(userActivitySchedule);
    }

    @Override
    public List<UserActivitySchedule> findAll() {
        return userActivityScheduleRepository.findAll();
    }

    @Override
    public UserActivitySchedule findByID(long id) {
        return userActivityScheduleRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Inscripción a la actividad no encontrada"));
    }
}
