package com.gimnasiolomas.ar.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UsersListDTO {
    private String activityName;
    private LocalDateTime activityClass;
}
