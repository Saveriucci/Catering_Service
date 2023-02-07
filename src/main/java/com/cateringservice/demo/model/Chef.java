package com.cateringservice.demo.model;

import java.util.LinkedList;
import java.util.List;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import javax.validation.constraints.NotBlank;




@Entity
public class Chef {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nomeChef;
	
	@NotBlank
	private String cognomeChef;
	
	@NotBlank
	private String nazionalita;
	
	private String immagine;

	@OneToMany (mappedBy = "chef", cascade = CascadeType.ALL)
	private List<Buffet> buffets;
	
	public Chef() {
		this.buffets = new LinkedList<Buffet>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeChef() {
		return nomeChef;
	}

	public void setNomeChef(String nomeChef) {
		this.nomeChef = nomeChef;
	}

	public String getCognomeChef() {
		return cognomeChef;
	}

	public void setCognomeChef(String cognomeChef) {
		this.cognomeChef = cognomeChef;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Buffet> getBuffets() {
		return buffets;
	}

	public void setBuffets(List<Buffet> buffets) {
		this.buffets = buffets;
	}
	
	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

}
