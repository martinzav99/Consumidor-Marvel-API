package com.martinzav.marvelChallenge.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martinzav.marvelChallenge.persistence.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);
    
}
