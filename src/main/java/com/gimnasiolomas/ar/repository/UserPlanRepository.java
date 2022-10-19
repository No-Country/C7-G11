package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
}
