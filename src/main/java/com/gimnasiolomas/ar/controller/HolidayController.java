package com.gimnasiolomas.ar.controller;

import com.gimnasiolomas.ar.dto.HolidayDTO;
import com.gimnasiolomas.ar.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/holidays")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @GetMapping
    public List<HolidayDTO> getAllHolidays(){
        return holidayService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addNewHoliday(@RequestBody HolidayDTO holidayDTO){
        return ResponseEntity.ok(holidayService.saveHoliday(holidayDTO));
    }
}
