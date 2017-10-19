package com.cac.dsi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cac.dsi.entites.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

	@Query("select d from Demande d where prod_id = :x order by date desc ")
	public List<Demande> demandeByPro(@Param("x") Long id);

	@Query("select d from Demande d where demandeur_id = :x order by date desc")
	public List<Demande> demandeByDem(@Param("x") Long id);

	@Transactional
	@Modifying
	@Query("delete from Demande d where demandeur_id = :x")
	public void deleteDemandeByDemandeur(@Param("x") Long id);
	
	@Transactional
	@Modifying
	@Query("delete from Demande d where prod_id = :x")
	public void deleteDemandeByProd(@Param("x") Long id);
}
