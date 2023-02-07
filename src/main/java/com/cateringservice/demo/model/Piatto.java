package com.cateringservice.demo.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.validation.constraints.NotBlank;


@Entity
public class Piatto {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomePiatto;
	
	@NotBlank
	private String descrizione;
	
	private String immagine;
	
	@OneToMany( mappedBy = "piatto", cascade = CascadeType.ALL)
	private List<Ingrediente> ingredienti;
	
	@ManyToOne( cascade = CascadeType.ALL)
	private Buffet buffet;
	
	public Piatto() {
		this.ingredienti = new LinkedList<Ingrediente>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomePiatto() {
		return nomePiatto;
	}

	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}
	
	
	
	
}
