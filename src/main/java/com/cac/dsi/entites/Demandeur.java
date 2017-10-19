package com.cac.dsi.entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Demandeur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nom;
	private String matr;
	private String description;
	@ManyToOne
	private Departement departement;
	@OneToMany(mappedBy = "demandeur")
	private List<Demande> demandes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public String getMatr() {
		return matr;
	}

	public void setMatr(String matr) {
		this.matr = matr;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Demandeur(String nom, String matr, String description) {
		super();
		this.nom = nom;
		this.matr = matr;
		this.description = description;
	}

	public Demandeur() {
		super();
	}

}
