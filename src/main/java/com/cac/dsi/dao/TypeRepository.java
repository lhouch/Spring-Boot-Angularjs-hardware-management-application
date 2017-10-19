package com.cac.dsi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cac.dsi.entites.Typemachine;

public interface TypeRepository extends JpaRepository<Typemachine, Long> {
			@Query("select t from Typemachine t where t.label like :x")
			public Page<Typemachine> chercherType(@Param("x")String mc, Pageable pageable);
			
}
