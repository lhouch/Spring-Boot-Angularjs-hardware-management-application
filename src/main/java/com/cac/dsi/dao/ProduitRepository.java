package com.cac.dsi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cac.dsi.entites.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
	@Query("select p from Produit p where type_id = :y and (p.label like :x or p.marque like :x or p.model like :x or p.description like :x or p.numeros like :x) ")
	public Page<Produit> chercherProduits(@Param("y") Long id,
			@Param("x") String mc, Pageable pageable);

	@Query("select p from Produit p where type_id = :x")
	public Page<Produit> produitsByType(@Param("x") Long id, Pageable pageable);

	/*
	 * (select p from Produit p LEFT JOIN p.demande d where d.delvre = true and type_id	 = :x) 
	 */
	@Query("select p from Produit p where id not in (select p from Produit p LEFT JOIN p.demande d where d.delvre = true and type_id= :x) and type_id= :x ")
	public Page<Produit> produitsDelvre(Pageable pageable, @Param("x") long id);

	// @Param("x") Long id, Pageable pageable

	@Query("SELECT p FROM Produit p WHERE p.numeros = :x AND p.numeros IS NOT NULL")
	public Produit ProByNumero(@Param("x") String numero);
	
	@Query("select COUNT(p) from Produit p where id not in (select p from Produit p LEFT JOIN p.demande d where d.delvre = true and type_id= :x) and type_id= :x ")
	public Long CountLBT(@Param("x") long id);
	
	@Query("SELECT COUNT(p) FROM Produit p where type_id = :x")
	public Long ProCount(@Param("x") Long id);
}
