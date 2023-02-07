package com.cateringservice.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cateringservice.demo.model.Ingrediente;
import com.cateringservice.demo.model.Piatto;
import com.cateringservice.demo.service.IngredienteService;
import com.cateringservice.demo.service.PiattoService;
import com.cateringservice.demo.validator.IngredienteValidator;

import javax.validation.Valid;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private IngredienteValidator ingredienteValidator;
	
	MultipartFile file;
	
	//UTENTE GENERICO
	
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente/ingrediente";
	}
	
	@GetMapping("/ingrediente/")
	public String getIngredienteAll( Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return "ingrediente/ingredienti";
	}

	@GetMapping("/ingrediente/ricerca/")
	public String getIngredienteRicercaForm( Model model) {
		model.addAttribute("ingredienti", ingredienteService.findAll());
		return "ingrediente/ingredienteRicercaForm";
	}
	
	@PostMapping("/ingrediente/ricerca/")
	public String getIngredienteByNome( @RequestParam("nomeIngrediente") String nomeIngrediente, Model model) {
		Ingrediente ingrediente = ingredienteService.findByNome(nomeIngrediente);
		if(ingredienteService.findByNome(nomeIngrediente) != null) {
			model.addAttribute("ingrediente", ingrediente);
			return "ingrediente/ingrediente";
		}
		return "ingrediente/ingredienteRicercaFormErrore";
	}
	
	
	//AMMINISTRATORE
	
	//INSERIMENTO
	
	@GetMapping("/ingrediente/add/")
	public String showIngredienteAddForm (Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "ingrediente/ingredienteForm";
	}
	
	@PostMapping("/ingrediente/add/")
	public String saveIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();

		ingredienteValidator.validate(ingrediente, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeFile != null)
				ingrediente.setImmagine("..\\images\\ingrediente\\" + nomeFile);
			else
				ingrediente.setImmagine("..\\images\\ingrediente\\sample.jpg");

			ingredienteService.saveIngrediente(ingrediente);
			model.addAttribute("ingrediente",ingrediente);
			return "ingrediente/ingrediente";
		}
		return "ingrediente/ingredienteForm";
	}
	
	@GetMapping("/ingrediente/caricaImmagine")
	public String caricaImmagineFormShow(Model model){
		model.addAttribute("immagine",new Object());
		return "ingrediente/immagineForm";
	}

	@PostMapping("/ingrediente/caricaImmagine")
	public String caricaImmagine( @RequestParam("immagine") MultipartFile immagine, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			ingredienteService.saveImmagine(immagine);
			return "ingrediente/immagineOk";
		} catch (IOException e) {
			return "ingrediente/immagineForm";
		}
	}
	
	@GetMapping("/ingrediente/add/piatto/{id}")
	public String showFormInserimentoIngredientiPiatti(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		model.addAttribute("listaPiatti", piattoService.findAll());
		return "ingrediente/ingredientePiatto";
	}

	@PostMapping("/ingrediente/add/piatto/{id}")
	public String aggiungiIngredienteAlPiatto( @PathVariable("id") Long id, @RequestParam("nomePiatto") String nomePiatto, Model model) {
		Piatto piatto = piattoService.findByNomePiatto(nomePiatto);
		if(piatto != null) {
			Ingrediente ingrediente = ingredienteService.findById(id);
			ingrediente.setPiatto(piatto);
			ingredienteService.update(ingrediente);
			model.addAttribute("piatto", piatto);
			return "ingrediente/ingredientePiattoOk";
		}
		model.addAttribute("id", id);
		return "ingrediente/ingredientePiattoErrore";
	}
	
	
	//CANCELLAZIONE
	
	@GetMapping("/ingrediente/delete/{id}")
	public String deleteById(@PathVariable("id") Long id, Model model) {
		ingredienteService.deleteIngrediente(id);
		return "ingrediente/ingredienteDeleteOk";
	}
	
	@GetMapping("/ingrediente/delete")
	public String deleteAll( Model model) {
		ingredienteService.deleteAll();
		return "index";
	}
	
	
	//AGGIORNAMENTO
	@GetMapping("ingrediente/edit/{id}")
	public String showIngredienteEditScelta( @PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente/ingredienteEditScelta";
	}
	
	@GetMapping("ingrediente/edit/edit/{id}")
	public String showChefEditForm(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente/ingredienteEdit";
	}
	
	@PostMapping("ingrediente/edit/edit/{id}")
	public  String update(@Valid@ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model ) {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();
		
		if(nomeFile != null)
			ingrediente.setImmagine("..\\images\\ingrediente\\" + nomeFile);
		else
			ingrediente.setImmagine("..\\images\\ingrediente\\sample.jpg");

		
		if(!bindingResult.hasErrors()) {
			ingredienteService.update(ingrediente);
			return "ingrediente/ingredienti";
		}
		return "ingrediente/ingredienteEdit";
	}
	
	@GetMapping("/ingrediente/edit/caricaImmagine/{id}")
	public String caricaImmagineFormEditShow(@PathVariable("id") Long id, Model model){
		model.addAttribute("immagine",new Object());
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente/immagineForm";
	}

	@PostMapping("/ingrediente/edit/caricaImmagine/{id}")
	public String caricaImmagineEdit( @RequestParam("immagine") MultipartFile immagine, @PathVariable("id") Long id, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			ingredienteService.saveImmagine(immagine);
			Ingrediente ingrediente = ingredienteService.findById(id);
			
			String nomeFile = null;
			if(file != null)
				nomeFile =file.getOriginalFilename();
			
			if(nomeFile != null)
				ingrediente.setImmagine("..\\images\\ingrediente\\" + nomeFile);
			else
				ingrediente.setImmagine("..\\images\\ingrediente\\sample.jpg");
			ingredienteService.update(ingrediente);
			return "ingrediente/immagineEditOk";
		} catch (IOException e) {
			return "ingrediente/immagineForm";
		}
	}

}
