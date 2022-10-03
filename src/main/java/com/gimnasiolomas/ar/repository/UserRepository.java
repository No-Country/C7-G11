package com.gimnasiolomas.ar.repository;

import com.gimnasiolomas.ar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findByEmailOrPassword(String email, String password);

}
