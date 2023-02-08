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
import com.cateringservice.demo.repository.BuffetRepository;
import com.cateringservice.demo.repository.ChefRepository;


@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;

	@Autowired 
	private ChefRepository chefRepository;

	private byte[] bytes;

	@Transactional
	public void saveBuffet( Buffet buffet) {
		this.buffetRepository.save(buffet);
	}

	@Transactional
	public void deleteBuffet(Long id) {
		this.buffetRepository.delete(buffetRepository.findById(id).get());
	}

	@Transactional
	public void deleteAll() {
		chefRepository.deleteAll();
	}

	@Transactional
	public void update(Buffet buffet) {
		Buffet buffet1 = buffetRepository.findById(buffet.getId()).get();
		buffet1.setNomeBuffet(buffet.getNomeBuffet());
		buffet1.setDescrizione(buffet.getDescrizione());
		buffet1.setPiatti(buffet.getPiatti());
		this.buffetRepository.save(buffet1);
	}
	
	@Transactional
	public void updateImmagine(Buffet buffet) {
		Buffet buffet1 = buffetRepository.findById(buffet.getId()).get();
		buffet1.setImmagine(buffet.getImmagine());
	}

	public Buffet findById( Long id) {
		return this.buffetRepository.findById(id).get();
	}

	public Buffet findByNomeBuffet(String nome) {
		Buffet buffet = buffetRepository.findByNomeBuffet(nome);
		return buffet;
	}

	public List<Buffet> findAll(){
		List<Buffet> buffets = new ArrayList<Buffet>();
		for( Buffet buffet: buffetRepository.findAll()) {
			buffets.add(buffet);
		}
		return buffets;
	}

	public List<Buffet> findByChef(Chef chef){
		return this.buffetRepository.findByChef(chef);
	}

	public List<Buffet> firstInsertedBuffet(){
		return this.buffetRepository.findTop3ByOrderById();
	}

	public boolean existsByNomebuffet(String nome) {
		return this.buffetRepository.existsByNomeBuffet(nome);
	}

	public void saveImmagine( MultipartFile immagine) throws IOException {
		//creo il path dove andro a salvare l immagine
		if(immagine != null) {
			String path = "C:\\Users\\Thomas\\Documents\\Catering_Service\\src\\main\\resources\\static\\images\\buffet\\" + immagine.getOriginalFilename();

			bytes = immagine.getBytes();

			//creo il path
			Path pathFinale = Paths.get(path);

			//scrivo il file su disco
			Files.write(pathFinale, bytes);

		}
	}

	public void saveChef(Long id, Buffet buffet) {
		Chef chef = chefRepository.findById(id).get();
		buffet.setChef(chef);
		this.update(buffet);
	}


}
