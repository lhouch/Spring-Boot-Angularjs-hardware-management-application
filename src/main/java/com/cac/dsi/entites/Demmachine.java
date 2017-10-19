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
public class Demmachine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;
	@Id
	@GeneratedValue
	private long id;
	private String titre;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String description;
	@ManyToOne
	private Demandeur demandeur;
	@Column(nullable = false)
	private boolean vsible;

	public Demmachine() {
		super();
	}

	public Demmachine(String titre, Date date, String description,
			Demandeur demandeur) {
		super();
		this.titre = titre;
		this.date = date;
		this.description = description;
		this.demandeur = demandeur;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isVsible() {
		return vsible;
	}

	public void setVsible(boolean vsible) {
		this.vsible = vsible;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Demandeur getDemandeur() {
		return demandeur;
	}

	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}

}
