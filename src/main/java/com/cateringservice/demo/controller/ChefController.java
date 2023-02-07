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
import com.cateringservice.demo.validator.ChefValidator;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	BuffetService buffetService;

	@Autowired
	private ChefValidator chefValidator;

	MultipartFile file;

	//UTENTE GENERICO

	@GetMapping("/chef/{id}")
	public String getChefById(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		return "chef/chef";
	}

	@GetMapping("/chef/")
	public String getAllChefs(Model model) {
		List<Chef> chefs = chefService.findAll();
		model.addAttribute("chefs", chefs);
		return "chef/chefs";
	}

	@GetMapping("/chef/ricerca/")
	public String getChefRicercaForm( Model model) {
		return "chef/chefRicercaForm";
	}

	@PostMapping("/chef/ricerca/")
	public String getChefByNome( @RequestParam("nazionalita") String nazionalita, Model model) {
		List<Chef> chefsByNazionalita = chefService.findByNazionalita(nazionalita);
		if(!chefsByNazionalita.isEmpty()) {
			model.addAttribute("chefs", chefsByNazionalita);
			return "chef/chefs";
		}
		return "chef/chefRicercaFormErrore";
	}


	//AMMINISTRATORE


	//INSERIMENTO
	@GetMapping("/chef/add/")
	public String getChefAddForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "chef/chefForm";
	}

	@PostMapping("/chef/add/")
	public String saveChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();

		chefValidator.validate(chef, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeFile != null)
				chef.setImmagine("..\\images\\chef\\" + nomeFile);
			else
				chef.setImmagine("..\\images\\chef\\sample.jpg");
			chefService.saveChef(chef);
			model.addAttribute("chef", chef);
			return "chef/chefOk";
		}
		else
			return "chef/chefForm";
	}

	@GetMapping("/chef/add/listaBuffet/{id}")
	public String showListaBuffetChoice(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("listaBuffets", buffets);
		model.addAttribute("prova", new String());
		return "chef/chefListaBuffetChoice";
	}

	@PostMapping("/chef/add/listaBuffet/{id}")
	public String aggiungiBuffetAlloChef(@PathVariable("id") Long id, @RequestParam("nomeChef") String nomeBuffet, Model model) {
		Buffet buffet = buffetService.findByNomeBuffet(nomeBuffet);
		if(buffet != null) {
			Chef chef = chefService.findById(id);
			chefService.saveBuffetToChef(chef, buffet);
			buffetService.saveChef(chef.getId(), buffet);
			model.addAttribute("buffet", buffet);
			return "chef/chefBuffetOk";
		}
		model.addAttribute("id", id);
		return "chef/chefBuffetErrore";
	}


	@GetMapping("/chef/caricaImmagine")
	public String caricaImmagineFormShow(Model model){
		model.addAttribute("immagine",new Object());
		return "chef/immagineForm";
	}

	@PostMapping("/chef/caricaImmagine")
	public String caricaImmagine( @RequestParam("immagine") MultipartFile immagine, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			chefService.saveImmagine(immagine);
			return "chef/immagineOk";
		} catch (IOException e) {
			return "chef/immagineForm";
		}
	}

	//CANCELLAZIONE

	@GetMapping("/chef/delete/{id}")
	public String deleteChefById(@PathVariable("id") Long id, Model model) {
		chefService.deleteChef(id);
		return "chef/chefDeletedOk";
	}

	@GetMapping("/chef/delete/")
	public String deleteAllChefs(Model model) {
		chefService.deleteAll();
		return "index";
	}


	//AGGIORNAMENTO

	@GetMapping("chef/edit/{id}")
	public String showScheltaEditForm(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		return "chef/chefEditScelta";
	}

	@GetMapping("chef/edit/edit/{id}")
	public String showChefEditForm(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		return "chef/chefEdit";
	}

	@PostMapping("chef/edit/edit/{id}")
	public String updateChef(@ModelAttribute("chef") Chef chef, Model model, BindingResult bindingResult) {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();

		if(nomeFile != null)
			chef.setImmagine("..\\images\\chef\\" + nomeFile);
		else
			chef.setImmagine("..\\images\\chef\\sample.jpg");

		if(!bindingResult.hasErrors()) {
			chefService.update(chef);
			return "chef/chef";
		}
		return "chef/chefEdit";
	}

	@GetMapping("/chef/edit/caricaImmagine/{id}")
	public String caricaImmagineFormEditShow(@PathVariable("id") Long id, Model model){
		model.addAttribute("immagine",new Object());
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
		return "chef/immagineForm";
	}

	@PostMapping("/chef/edit/caricaImmagine/{id}")
	public String caricaImmagineEdit( @RequestParam("immagine") MultipartFile immagine, @PathVariable("id") Long id, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			chefService.saveImmagine(immagine);
			Chef chef = chefService.findById(id);

			String nomeFile = null;
			if(file != null)
				nomeFile =file.getOriginalFilename();

			if(nomeFile != null)
				chef.setImmagine("..\\images\\chef\\" + nomeFile);
			else
				chef.setImmagine("..\\images\\chef\\sample.jpg");
			chefService.update(chef);
			return "chef/immagineEditOk";
		} catch (IOException e) {
			return "chef/immagineForm";
		}
	}


}
