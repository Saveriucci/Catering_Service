package com.cateringservice.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.cateringservice.demo.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
	
	public Ingrediente findByNome( String nome);
	
	public boolean existsByNomeAndOrigine( String nomeIngrediente, String origine);

}
