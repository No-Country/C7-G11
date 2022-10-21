package com.gimnasiolomas.ar.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UsersListDTO {
    @NotBlank
    private String activityName;
    @NotBlank
    private LocalDateTime activityClass;
}
