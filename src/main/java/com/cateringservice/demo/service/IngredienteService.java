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

import com.cateringservice.demo.model.Ingrediente;
import com.cateringservice.demo.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	IngredienteRepository ingredienteRepository;
	
	@Autowired
	PiattoService piattoService;
	
	/*@Transactional
	public void saveIngredientiPiatto(List<Ingrediente> ingredienti, Piatto piatto) {
		for(Ingrediente ingrediente: ingredienti) {
			ingredienteRepository.save(ingrediente);
		}
		piatto.setIngredienti(ingredienti);
		piattoService.savePiatto(piatto);
	}*/
	
	@Transactional
	public void saveIngrediente(Ingrediente ingrediente) {
		ingredienteRepository.save(ingrediente);
	}
	
	@Transactional
	public void deleteIngrediente(Long id) {
		ingredienteRepository.delete(ingredienteRepository.findById(id).get());
	}
	
	@Transactional
	public void deleteAll() {
		ingredienteRepository.deleteAll();
	}
	
	@Transactional
	public void update(Ingrediente ingrediente) {
		Ingrediente ingrediente1 = ingredienteRepository.findById(ingrediente.getId()).get();
		ingrediente1.setDescrizione(ingrediente.getDescrizione());
		ingrediente1.setNome(ingrediente.getNome());
		ingrediente1.setOrigine(ingrediente.getOrigine());
		ingrediente1.setImmagine(ingrediente.getImmagine());
	}
	
	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id).get();
	}
	
	public Ingrediente findByNome(String nome) {
		return ingredienteRepository.findByNome(nome);
	}
	
	public List<Ingrediente> findAll(){
		List<Ingrediente> ingredienti = new ArrayList<Ingrediente>();
		for( Ingrediente ingr: ingredienteRepository.findAll()) {
			ingredienti.add(ingr);
		}
		return ingredienti;
	}
	
	public boolean existByNomeAndOrigine(String nome, String origine) {
		return ingredienteRepository.existsByNomeAndOrigine(nome, origine);
	}

	public void saveImmagine( MultipartFile immagine) throws IOException {
		//creo il path dove andro a salvare l immagine
		if(immagine != null) {
			String path = "C:\\Users\\Thomas\\Documents\\Catering_Service\\src\\main\\resources\\static\\images\\ingrediente\\" + immagine.getOriginalFilename();

			byte[] bytes = immagine.getBytes();

			//creo il path
			Path pathFinale = Paths.get(path);

			//scrivo il file su disco
			Files.write(pathFinale, bytes);

		}
	}


}
