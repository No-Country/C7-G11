package com.gimnasiolomas.ar.service;

import com.gimnasiolomas.ar.dto.HolidayDTO;
import com.gimnasiolomas.ar.entity.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface HolidayService {
    HolidayDTO saveHoliday(HolidayDTO holidayDTO);
    List<HolidayDTO> findAll();
    Holiday findByDate(LocalDate localDate);
}
