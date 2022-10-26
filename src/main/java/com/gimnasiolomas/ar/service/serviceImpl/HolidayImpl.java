package com.gimnasiolomas.ar.service.serviceImpl;

import com.gimnasiolomas.ar.dto.HolidayDTO;
import com.gimnasiolomas.ar.entity.Holiday;
import com.gimnasiolomas.ar.repository.HolidayRepository;
import com.gimnasiolomas.ar.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayImpl implements HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    public HolidayDTO saveHoliday(HolidayDTO holidayDTO) {
        Holiday holiday = new Holiday(holidayDTO.getName(), holidayDTO.getDate());
        holidayRepository.save(holiday);
        return new HolidayDTO(holiday);
    }

    @Override
    public List<HolidayDTO> findAll() {
        return holidayRepository.findAll().stream().map(HolidayDTO::new).collect(Collectors.toList());
    }

    @Override
    public Holiday findByDate(LocalDate localDate) {
        return holidayRepository.findByDate(localDate).orElse(null);
    }
}
