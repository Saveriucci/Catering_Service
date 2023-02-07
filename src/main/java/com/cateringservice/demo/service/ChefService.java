package com.cateringservice.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cateringservice.demo.model.Buffet;
import com.cateringservice.demo.model.Chef;
import com.cateringservice.demo.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepository;
	
	
	@Transactional
	public void saveChef(Chef chef) {
		chefRepository.save(chef);
	}
	
	public void saveBuffetToChef(Chef chef, Buffet buffet) {
		chef.getBuffets().add(buffet);
		this.update(chef);
	}
	
	@Transactional
	public void deleteChef(Long id) {
		Chef chef = chefRepository.findById(id).get();
		chefRepository.delete(chef);
	}
	
	@Transactional
	public void deleteAll() {
		chefRepository.deleteAll();
	}
	
	@Transactional
	public void update(Chef chef) {
		Chef chef1 = chefRepository.findById(chef.getId()).get();
		chef1.setNomeChef(chef.getNomeChef());
		chef1.setCognomeChef(chef.getCognomeChef());
		chef1.setBuffets(chef.getBuffets());
		chef1.setNazionalita(chef.getNazionalita());
		chefRepository.save(chef1);
	}

	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<Chef>();
		for(Chef chef : chefRepository.findAll()) {
			chefs.add(chef);
		}
		return chefs;
	}
	
	public List<Chef> findByNazionalita( String nazionalita){
		return chefRepository.findByNazionalita(nazionalita);
	}
	public boolean existsByNomeChefAndCognomeChefAndNazionalita( String nome, String cognome, String nazionalita) {
		return chefRepository.existsByNomeChefAndCognomeChefAndNazionalita(nome, cognome, nazionalita);
	}

	
	public List<Chef> firstInsertedChef(){
		return chefRepository.findTop3ByOrderById();
	}

	public void saveImmagine( MultipartFile immagine) throws IOException {
		//creo il path dove andro a salvare l immagine
		if(immagine != null) {
			String path = "C:\\Users\\Thomas\\Documents\\Catering_Service\\src\\main\\resources\\static\\images\\chef\\" + immagine.getOriginalFilename();

			byte[] bytes = immagine.getBytes();

			//creo il path
			Path pathFinale = Paths.get(path);

			//scrivo il file su disco
			Files.write(pathFinale, bytes);

		}
	}
}
