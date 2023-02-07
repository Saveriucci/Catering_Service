package com.cateringservice.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

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

import com.cateringservice.demo.model.Buffet;
import com.cateringservice.demo.model.Chef;
import com.cateringservice.demo.service.BuffetService;
import com.cateringservice.demo.service.ChefService;
import com.cateringservice.demo.validator.BuffetValidator;


@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetValidator buffetValidator;

	private MultipartFile file;


	//UTENTE GENERICO

	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model){
		Buffet buffet = buffetService.findById(id);
		if(buffet.getChef() != null) {
			Chef chef = chefService.findById(buffet.getChef().getId());
			model.addAttribute("chef", chef);
		}
		model.addAttribute("buffet", buffet);
		return "buffet/buffet";
	}

	@GetMapping("/buffet/")
	public String getBuffetAll( Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "buffet/buffets";
	}

	@GetMapping("/buffet/ricerca/")
	public String getBuffetRicercaForm( Model model) {
		return "buffet/buffetRicercaForm";
	}

	@PostMapping("/buffet/ricerca/")
	public String getBuffetByNome(@RequestParam("nomeBuffet") String nomeBuffet, Model model) {
		if(buffetService.findByNomeBuffet(nomeBuffet) != null) {
			Buffet buffet = buffetService.findByNomeBuffet(nomeBuffet);
			if(buffet != null) {
				model.addAttribute("buffet", buffet);
				return "buffet/buffet";
			}
		}
		return "buffet/buffetRicercaFormErrore";
	}


	//AMMINISTRATORE


	//INSERIMENTO
	@GetMapping("/buffet/add")
	public String showBuffetForm(Model model) {
		Buffet buffet = new Buffet();
		model.addAttribute("buffet", buffet);
		return "/buffet/buffetForm";
	}

	@PostMapping("/buffet/")
	//associo l oggetto buffet nel modello sotto il nome di "buffet"
	public String saveBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, Model model) throws IOException {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();
		buffetValidator.validate(buffet, bindingResult);

		//verifico se ci sono errori nell oggetto buffet
		if(!bindingResult.hasErrors()) {
			if(nomeFile != null)
				buffet.setImmagine("..\\images\\buffet\\" + nomeFile);
			else
				buffet.setImmagine("..\\images\\buffet\\sample.jpg");
			buffetService.saveBuffet(buffet);
			model.addAttribute("buffet", buffet);
			return "buffet/buffet";
		}

		//se i dati sono sbagliati
		return "buffet/buffetForm";
	}

	@GetMapping("/buffet/caricaImmagine")
	public String caricaImmagineFormShow(Model model){
		model.addAttribute("immagine",new Object());
		return "buffet/immagineForm";
	}

	@PostMapping("/buffet/caricaImmagine")
	public String caricaImmagine( @RequestParam("immagine") MultipartFile immagine, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			buffetService.saveImmagine(immagine);
			return "buffet/immagineOk";
		} catch (IOException e) {
			return "buffet/immagineForm";
		}
	}

	//CANCELLAZIONE

	//elimina un buffet tramite id 
	@GetMapping("/buffet/delete/{id}")
	public String deleteBuffetById(@PathVariable("id") Long id, Model model) {
		buffetService.deleteBuffet(id);
		return "buffet/buffetDeleteOk";
	}

	//elimina tutti i buffet presenti
	@GetMapping("/buffet/delete/")
	public String deleteAllBuffets(Model model) {
		buffetService.deleteAll();
		return "index";
	}


	//MODIFICA
	@GetMapping("/buffet/edit/{id}")
	public String showBuffetEditFormScelta(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffet/buffetEditScelta";
	}

	@GetMapping("buffet/edit/edit/{id}")
	public String showBuffetEditForm(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffet/buffetEdit";
	}

	@PostMapping("/buffet/edit/edit/{id}")
	public String editBuffet(@ModelAttribute("buffet")Buffet buffet, BindingResult bindingResult, @PathVariable("id")Long id) {
		if(!bindingResult.hasErrors()) {
		buffetService.update(buffet);
		return "buffet/buffet";
		}
		return "buffet/buffetEdit";
	}

	@GetMapping("/buffet/edit/caricaImmagine/{id}")
	public String caricaImmagineFormEditShow(@PathVariable("id") Long id,Model model){
		model.addAttribute("immagine",new Object());
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "buffet/immagineForm";
	}

	@PostMapping("/buffet/edit/caricaImmagine/{id}")
	public String caricaImmagineEdit( @RequestParam("immagine") MultipartFile immagine, @PathVariable("id") Long id, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			buffetService.saveImmagine(immagine);
			Buffet buffet = buffetService.findById(id);

			String nomeFile = null;
			if(file != null)
				nomeFile =file.getOriginalFilename();

			if(nomeFile != null)
				buffet.setImmagine("..\\images\\buffet\\" + nomeFile);
			else
				buffet.setImmagine("..\\images\\buffet\\sample.jpg");
			buffetService.updateImmagine(buffet);
			return "buffet/immagineEditOk";
		} catch (IOException e) {
			return "buffet/immagineForm";
		}
	}
}
