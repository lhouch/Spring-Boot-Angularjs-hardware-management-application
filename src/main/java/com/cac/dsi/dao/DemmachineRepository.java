package com.cac.dsi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cac.dsi.entites.Demmachine;

public interface DemmachineRepository extends JpaRepository<Demmachine, Long> {
	@Query("select d from Demmachine d where vsible = false")
	public List<Demmachine> getAllv();

	@Transactional
	@Modifying
	@Query("delete from Demmachine d where demandeur_id = :x")
	public void deleteDemmByDemandeur(@Param("x") Long id);

}
