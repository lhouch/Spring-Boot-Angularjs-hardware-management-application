package com.cac.dsi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cac.dsi.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.nom LIKE :x OR u.email LIKE :x")
	public Page<User> findUsers(@Param("x") String mc, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.email = :x AND u.password = :y AND active = true")
	public User login(@Param("x") String email, @Param("y") String pass);

	@Query("SELECT u FROM User u WHERE u.email = :x")
	public User Vemail(@Param("x") String email);
}
