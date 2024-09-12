package com.signimusTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.signimusTask.entity.User;


public interface UserRepo extends JpaRepository<User, Integer>{

	public User findByEmail(String emaill);
}
