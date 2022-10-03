package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.ActivitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityScheduleRepository extends JpaRepository<ActivitySchedule, Long> {
}
