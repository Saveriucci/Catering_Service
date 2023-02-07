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

import com.cateringservice.demo.model.Piatto;
import com.cateringservice.demo.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepository piattoRepository;

	@Transactional
	public void savePiatto( Piatto piatto) {
		piattoRepository.save(piatto);
	}

	@Transactional
	public void deletePiatto( Long id) {
		piattoRepository.delete(piattoRepository.findById(id).get());
	}

	@Transactional
	public void deleteAll() {
		piattoRepository.deleteAll();
	}

	@Transactional
	public void update( Piatto piatto) {
		Piatto piatto1 = piattoRepository.findById(piatto.getId()).get();
		piatto1.setDescrizione(piatto.getDescrizione());
		piatto1.setIngredienti(piatto.getIngredienti());
		piatto1.setNomePiatto(piatto.getNomePiatto());
		piattoRepository.save(piatto1);
	}

	public Piatto findById( Long id) {
		return piattoRepository.findById(id).get();
	}

	public List<Piatto> findAll(){
		List<Piatto> piatti = new ArrayList<Piatto>();
		for( Piatto piatto: piattoRepository.findAll()) {
			piatti.add(piatto);
		}
		return piatti;
	}

	public Piatto findByNomePiatto(String nomePiatto) {
		return piattoRepository.findByNomePiatto(nomePiatto);
	}

	public boolean existsByNomePiatto( String nomePiatto) {
		return piattoRepository.existsBynomePiatto(nomePiatto);
	}

	public void saveImmagine(MultipartFile immagine) throws IOException {
		//creo il path dove andro a salvare l immagine
		if(immagine != null) {
			String path = "C:\\Users\\Thomas\\Documents\\Catering_Service\\src\\main\\resources\\static\\images\\piatto\\" + immagine.getOriginalFilename();

			byte[] bytes = immagine.getBytes();

			//creo il path
			Path pathFinale = Paths.get(path);

			//scrivo il file su disco
			Files.write(pathFinale, bytes);



		}
	}

	public List<Piatto> firstInsertedPlates() {
		return piattoRepository.findTop3ByOrderById();
	}

}
