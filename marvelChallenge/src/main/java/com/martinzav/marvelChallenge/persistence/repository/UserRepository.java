package com.martinzav.marvelChallenge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martinzav.marvelChallenge.persistence.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
