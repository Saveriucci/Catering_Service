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
import javax.validation.constraints.Size;

@Entity
public class Buffet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeBuffet;
	
	@NotBlank
	private String descrizione;
	
	@Size( max=500)
	private String immagine;
	
	@ManyToOne( cascade = CascadeType.ALL)
	private Chef chef;
	
	@OneToMany(mappedBy = "buffet", cascade = CascadeType.ALL)
	private List<Piatto> piatti;
	
	public Buffet() {
		this.piatti = new LinkedList<Piatto>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeBuffet() {
		return nomeBuffet;
	}

	public void setNomeBuffet(String nomeBuffet) {
		this.nomeBuffet = nomeBuffet;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	
	
}
