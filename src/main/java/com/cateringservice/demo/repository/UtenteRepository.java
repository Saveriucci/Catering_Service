package com.cateringservice.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.cateringservice.demo.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long>{
	
	public Utente findByUsername(String username);
	
	public boolean existsByUsername( String username);

}
