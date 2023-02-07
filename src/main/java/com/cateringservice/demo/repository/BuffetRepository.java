package com.cateringservice.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cateringservice.demo.model.Buffet;
import com.cateringservice.demo.model.Chef;



public interface BuffetRepository extends CrudRepository<Buffet, Long>{
	
	public Buffet findByNomeBuffet(String nome);
	
	public boolean existsByNomeBuffet(String nome);
	
	public List<Buffet> findByChef(Chef chef);
	
	public boolean deleteByChef(Chef chef);
	
	List<Buffet> findTop3ByOrderById();



}
