package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByActivityName(String activityName);
}
