package com.gimnasiolomas.ar;

import com.gimnasiolomas.ar.entity.Activity;
import com.gimnasiolomas.ar.entity.ActivitySchedule;
import com.gimnasiolomas.ar.entity.Schedule;
import com.gimnasiolomas.ar.entity.WeekDay;
import com.gimnasiolomas.ar.repository.ActivityRepository;
import com.gimnasiolomas.ar.repository.ActivityScheduleRepository;
import com.gimnasiolomas.ar.repository.ScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GimnasioSpringApplication{

	public static void main(String[] args) {
		SpringApplication.run(GimnasioSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ActivityScheduleRepository activityScheduleRepository, ActivityRepository activityRepository, ScheduleRepository scheduleRepository) {
		return (args) -> {
//			String sql = "INSERT INTO User (name, last_name, email, password) VALUES (?, ?, ?, ?)";
//			int result = jdbcTemplate.update(sql, "Esteban", "Casile", "esteban@gmail.com", "123456");
//			if (result > 0) {
//				System.out.println("Agregado");
//			}
//			System.out.println("Gimnasio Lomas");



//			Activity activity = new Activity("Pilates");
//			activityRepository.save(activity);
//			Schedule schedule = new Schedule(WeekDay.LUNES, 20);
//			scheduleRepository.save(schedule);
//			ActivitySchedule activitySchedule = new ActivitySchedule(activity, schedule);
//			activityScheduleRepository.save(activitySchedule);
//			Schedule schedule1 = new Schedule(WeekDay.LUNES, 19);
//			scheduleRepository.save(schedule1);
//			ActivitySchedule activitySchedule1 = new ActivitySchedule(activity, schedule1);
//			activityScheduleRepository.save(activitySchedule1);
//			Schedule schedule2 = new Schedule(WeekDay.LUNES, 21);
//			scheduleRepository.save(schedule2);
//			ActivitySchedule activitySchedule2 = new ActivitySchedule(activity, schedule2);
//			activityScheduleRepository.save(activitySchedule2);
//			Schedule schedule3 = new Schedule(WeekDay.LUNES, 22);
//			scheduleRepository.save(schedule3);
//			ActivitySchedule activitySchedule3 = new ActivitySchedule(activity, schedule3);
//			activityScheduleRepository.save(activitySchedule3);

//			LocalDate localDate = LocalDate.now();
//			DayOfWeek dayOfWeek = localDate.getDayOfWeek();
//			System.out.println(dayOfWeek);
		};
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("/**")
//						.allowedMethods("*")
//						.allowedHeaders("*");
//			}
//		};
//	}
}
