package com.cateringservice.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cateringservice.demo.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long>{

	public Piatto findByNomePiatto(String nomePiatto);
	
	public boolean existsBynomePiatto(String nome);

	public List<Piatto> findTop3ByOrderById();
}
