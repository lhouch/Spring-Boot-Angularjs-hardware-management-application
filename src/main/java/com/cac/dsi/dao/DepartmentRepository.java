package com.cac.dsi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cac.dsi.entites.Departement;

public interface DepartmentRepository extends JpaRepository<Departement, Long> {
	@Query("select d from Departement d where d.nom like :x")
	public Page<Departement> chercherDeppartement(@Param("x") String mc,
			Pageable pageable);

}
