package com.gimnasiolomas.ar.dto;

import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class InscriptionDTO {
    @NotBlank
    private String activityName;
    @Future
    private LocalDateTime dayHourActivity;
}
