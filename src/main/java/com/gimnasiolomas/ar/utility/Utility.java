package com.gimnasiolomas.ar.utility;

import com.gimnasiolomas.ar.constants.Messages;
import com.gimnasiolomas.ar.entity.User;
import com.gimnasiolomas.ar.entity.UserPlan;
import com.gimnasiolomas.ar.entity.WeekDay;
import com.gimnasiolomas.ar.error.NotFoundException;
import com.gimnasiolomas.ar.repository.UserPlanRepository;
import com.gimnasiolomas.ar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Utility {
    @Autowired
    private static UserRepository userRepository;
    @Autowired
    private static UserPlanRepository userPlanRepository;

    public static WeekDay translateDay(DayOfWeek dayOfWeek){
        if(dayOfWeek.equals(DayOfWeek.MONDAY)){
            return WeekDay.LUNES;
        }
        if(dayOfWeek.equals(DayOfWeek.TUESDAY)){
            return WeekDay.MARTES;
        }
        if(dayOfWeek.equals(DayOfWeek.WEDNESDAY)){
            return WeekDay.MIERCOLES;
        }
        if(dayOfWeek.equals(DayOfWeek.THURSDAY)){
            return WeekDay.JUEVES;
        }
        if(dayOfWeek.equals(DayOfWeek.FRIDAY)){
            return WeekDay.VIERNES;
        }
        if(dayOfWeek.equals(DayOfWeek.SATURDAY)){
            return WeekDay.SABADO;
        }
        if(dayOfWeek.equals(DayOfWeek.SUNDAY)){
            return WeekDay.DOMINGO;
        }
        return null;
    }
    public static DayOfWeek translateDayEs(WeekDay weekDay){
        if(weekDay.equals(WeekDay.LUNES)){
            return DayOfWeek.MONDAY;
        }
        if(weekDay.equals(WeekDay.MARTES)){
            return DayOfWeek.TUESDAY;
        }
        if(weekDay.equals(WeekDay.MIERCOLES)){
            return DayOfWeek.WEDNESDAY;
        }
        if(weekDay.equals(WeekDay.JUEVES)){
            return DayOfWeek.THURSDAY;
        }
        if(weekDay.equals(WeekDay.VIERNES)){
            return DayOfWeek.FRIDAY;
        }
        if(weekDay.equals(WeekDay.SABADO)){
            return DayOfWeek.SATURDAY;
        }
        if(weekDay.equals(WeekDay.DOMINGO)){
            return DayOfWeek.SUNDAY;
        }
        return null;
    }
    public static WeekDay changeToUpperCase(String word){
        for(WeekDay weekDay : WeekDay.values()){
            if(word.toUpperCase().equals(weekDay.toString())){
                return weekDay;
            }
        }
        return null;
    }

    public static void checkActivePlan(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(Messages.USER_NOT_FOUND));
        UserPlan userPlan = user.getUserPlans().stream().filter(UserPlan::isActive).findFirst().orElseThrow(()-> new NotFoundException("No hay planes activos"));
        if(userPlan.getExpireDate().isAfter(LocalDate.now())){
            userPlan.setActive(false);
            userPlanRepository.save(userPlan);
        }
    }
}
