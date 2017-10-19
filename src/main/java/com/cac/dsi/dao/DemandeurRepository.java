package com.cac.dsi.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cac.dsi.entites.Demandeur;

public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
	@Query("select d from Demandeur d where departement_id = :y and (d.nom like :x or d.description like :x) ")
	public Page<Demandeur> chercherDemandeur(@Param("y") Long id,
			@Param("x") String mc, Pageable pageable);
	@Query("select d from Demandeur d where d.nom like :x or d.description like :x")
	public Page<Demandeur> chercher(@Param("x") String mc, Pageable pageable);

	@Query("select d from Demandeur d where demandeur_id = :x")
	public Page<Demandeur> demandeurByDepart(@Param("x") Long id,
			Pageable pageable);

	@Query("select d from Demandeur d where departement_id = :x")
	public Page<Demandeur> getAll(@Param("x") Long id, Pageable pageable);

	@Query("select d from Demandeur d where departement_id = :x")
	public List<Demandeur> getAlli(@Param("x") Long id);

	@Transactional
	@Modifying
	@Query("delete from Demandeur d where departement_id = :x")
	public void deleteDemandeurByDepartement(@Param("x") Long id);
}