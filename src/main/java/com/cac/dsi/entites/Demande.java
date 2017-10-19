package com.cac.dsi.entites;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Demande implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Produit prod;
	@ManyToOne
	private Demandeur demandeur;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(nullable = false)
	private boolean delvre;

	public boolean isDelvre() {
		return delvre;
	}

	public void setDelvre(boolean delvre) {
		this.delvre = delvre;
	}

	public Demande(Produit prod, Demandeur demandeur, Date date) {
		super();
		this.prod = prod;
		this.demandeur = demandeur;
		this.date = date;
	}

	public Demande(Produit prod, Date date) {
		super();
		this.prod = prod;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Produit getProd() {
		return prod;
	}

	public void setProd(Produit prod) {
		this.prod = prod;
	}

	public Demande() {
		super();
	}

	public Demandeur getDemandeur() {
		return demandeur;
	}

	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}

}
