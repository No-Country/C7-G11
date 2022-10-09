package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.UserActivitySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityScheduleRepository extends JpaRepository<UserActivitySchedule, Long> {
}
