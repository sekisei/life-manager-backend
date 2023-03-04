package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
	Optional<Users> findByUserName(String userName);
}
