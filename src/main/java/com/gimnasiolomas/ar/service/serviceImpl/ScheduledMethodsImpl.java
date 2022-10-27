package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.entity.ScheduleState;
import com.gimnasiolomas.ar.service.UserActivityScheduleService;
import com.gimnasiolomas.ar.service.UserPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@EnableScheduling
public class ScheduledMethodsImpl {

    @Autowired
    private UserPlanService userPlanService;
    @Autowired
    private UserActivityScheduleService userActivityScheduleService;

    @Scheduled(cron = "0 0 0 * * *")
    public void setFalseToExpiredMemberships(){
        userPlanService.findAll().stream()
                .filter(plan -> plan.getExpireDate().isEqual(LocalDate.now()))
                .forEach(plan -> {
                    plan.setActive(false);
                    userPlanService.save(plan);
                });
    }

    @Scheduled(cron = "0 0 * * * *")
    public void setAssistedClass(){
        userActivityScheduleService
                .findAll()
                .stream()
                .filter(act -> act.getDayHourActivity().toLocalDate().isEqual(LocalDate.now()))
                .filter(act -> act.getDayHourActivity().getHour() == LocalDateTime.now().getHour())
                .filter(act -> act.getState()== ScheduleState.INSCRIPTO)
                .forEach(act -> {
                    act.setState(ScheduleState.ASISTIDO);
                    userActivityScheduleService.save(act);
                });
    }
}
