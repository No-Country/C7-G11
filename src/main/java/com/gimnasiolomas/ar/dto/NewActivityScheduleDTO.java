package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.WeekDay;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
public class NewActivityScheduleDTO {

    @NotBlank
    private String activityName;
    private String weekDay;
    private int hour;
}
