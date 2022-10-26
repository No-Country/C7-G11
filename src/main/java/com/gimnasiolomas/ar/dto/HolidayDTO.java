package com.gimnasiolomas.ar.dto;

import com.gimnasiolomas.ar.entity.Holiday;
import java.time.LocalDate;

public class HolidayDTO {
    private long id;
    private String name;
    private LocalDate date;

    public HolidayDTO(){}
    public HolidayDTO(Holiday holiday){
        this.id = holiday.getId();
        this.name = holiday.getName();
        this.date = holiday.getDate();
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getDate() {
        return date;
    }
}
