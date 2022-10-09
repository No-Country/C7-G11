package com.gimnasiolomas.ar.utility;

import com.gimnasiolomas.ar.entity.WeekDay;

import java.time.DayOfWeek;

public class Utility {

    public WeekDay translateDay(DayOfWeek dayOfWeek){
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
}
