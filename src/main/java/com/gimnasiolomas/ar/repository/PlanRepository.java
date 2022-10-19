package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    public Plan findByName(String name);
}
