package com.example.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}