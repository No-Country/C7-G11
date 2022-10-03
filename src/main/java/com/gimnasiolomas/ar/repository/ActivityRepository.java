package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Activity findByActivityName(String activityName);
}
