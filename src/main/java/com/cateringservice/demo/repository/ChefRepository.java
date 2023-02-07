package com.cateringservice.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cateringservice.demo.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long>{
	
	public List<Chef> findByNazionalita( String Nazionalita);
	
	public boolean existsByNomeChefAndCognomeChefAndNazionalita( String nome, String cognome, String nazionalita);
	
	public List<Chef> findTop3ByOrderById();

}
