package com.cac.dsi.entites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Produit implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String label;
	private String marque;
	private String model;
	private Double prix;
	@Temporal(TemporalType.DATE)
	private Date garantie;
	private String description;
	private String numeros;
	@ManyToOne
	private Typemachine type;
	@OneToMany(mappedBy = "prod")
	private List<Demande> demande;

	public Produit(long id, String label, String marque, String model,
			Double prix, Date garantie, String description, String numeros) {
		super();
		this.id = id;
		this.label = label;
		this.marque = marque;
		this.model = model;
		this.prix = prix;
		this.garantie = garantie;
		this.description = description;
		this.numeros = numeros;
	}

	public Produit(String label, String marque, String model, Double prix,
			Date garantie, String description, String numeros) {
		super();
		this.label = label;
		this.marque = marque;
		this.model = model;
		this.prix = prix;
		this.garantie = garantie;
		this.description = description;
		this.numeros = numeros;
	}

	public Typemachine getType() {
		return type;
	}

	public void setType(Typemachine type) {
		this.type = type;
	}

	public Produit() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumeros() {
		return numeros;
	}

	public void setNumeros(String numeros) {
		this.numeros = numeros;
	}

	public Date getGarantie() {
		return garantie;
	}

	public void setGarantie(Date garantie) {
		this.garantie = garantie;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

}
