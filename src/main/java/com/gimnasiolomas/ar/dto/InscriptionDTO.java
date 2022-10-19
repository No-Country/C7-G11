package com.gimnasiolomas.ar.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class InscriptionDTO {
    @NotBlank
    private String activityName;
//    @NotBlank
//    private String weekDay;
//    @NotNull
//    @Min(8)
//    @Max(22)
//    private int hour;
    private LocalDateTime dayHourActivity;
}
