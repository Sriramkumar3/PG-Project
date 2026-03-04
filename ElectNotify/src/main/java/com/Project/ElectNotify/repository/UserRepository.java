package com.Project.ElectNotify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.ElectNotify.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
}
