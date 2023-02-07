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

import com.cateringservice.demo.model.Buffet;
import com.cateringservice.demo.model.Piatto;
import com.cateringservice.demo.service.BuffetService;
import com.cateringservice.demo.service.PiattoService;
import com.cateringservice.demo.validator.PiattoValidator;

import javax.validation.Valid;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;

	@Autowired
	private BuffetService buffetService;

	MultipartFile file;

	//UTENTE GENERICO

	@GetMapping("/piatto/{id}")
	public String findPiattoById(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "piatto/piatto";
	}

	@GetMapping("/piatto/")
	public String findPiattoAll( Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "piatto/piatti";
	}

	@GetMapping("/piatto/ricerca/")
	public String getPiattoRicercaForm( Model model) {
		return "piatto/piattoRicercaForm";
	}

	@PostMapping("/piatto/ricerca/")
	public String getPiattoByNome( @RequestParam("nomePiatto") String nomePiatto, Model model) {
		if(piattoService.findByNomePiatto(nomePiatto) != null) {
			Piatto piatto = piattoService.findByNomePiatto(nomePiatto);
			if(piatto != null) {
				model.addAttribute("piatto", piatto);
				return "piatto/piatto";
			}
		}
		return "piatto/piattoRicercaFormErrore";
	}

	//AMMINISTRATORE

	//NSERIMENTO

	@GetMapping("/piatto/add/")
	public String showPiattoAddForm(Model model) {
		model.addAttribute("piatto", new Piatto());
		return "piatto/piattoForm";
	}

	@PostMapping("/piatto/add/")
	public String savePiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();

		piattoValidator.validate(piatto, bindingResult);

		if(!bindingResult.hasErrors()) {
			if(nomeFile != null)
				piatto.setImmagine("..\\images\\piatto\\" + nomeFile);
			else
				piatto.setImmagine("..\\images\\piatto\\sample.jpg");
			piattoService.savePiatto(piatto);
			model.addAttribute("piatto", piatto);
			return "piatto/piatto";
		}
		else
			return "piatto/piattoForm";
	}

	@GetMapping("/piatto/caricaImmagine")
	public String caricaImmagineFormShow(Model model){
		model.addAttribute("immagine",new Object());
		return "piatto/immagineForm";
	}

	@PostMapping("/piatto/caricaImmagine")
	public String caricaImmagine( @RequestParam("immagine") MultipartFile immagine, Model model) throws IOException {
		file = immagine;
		model.addAttribute("immagine",immagine);
		piattoService.saveImmagine(immagine);
		return "piatto/immagineOk";
	}

	@GetMapping("/piatto/add/buffet/{id}")
	public String showAggiuntaBuffetAlPiattoForm(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		model.addAttribute("listaBuffets", buffetService.findAll());
		return "piatto/piattoBuffet";
	}

	@PostMapping("/piatto/add/buffet/{id}")
	public String aggiuntaBuffetAlPiattoForm(@PathVariable("id") Long id, @RequestParam("nomeBuffet") String nomeBuffet, Model model) {
		Buffet buffet = buffetService.findByNomeBuffet(nomeBuffet);
		if(buffet != null) {
			Piatto piatto = piattoService.findById(id);
			piatto.setBuffet(buffet);
			piattoService.savePiatto(piatto);
			model.addAttribute("buffet", buffet);
			return "piatto/piattoBuffetOk";
		}
		model.addAttribute("id", id);
		return "piatto/piattoBuffetErrore";
	}


	//CANCELLAZIONE

	@GetMapping("/piatto/delete/{id}")
	public String deletePiattoById(@PathVariable("id") Long id, Model model) {
		piattoService.deletePiatto(id);
		return "piatto/piattoDeleteOk";
	}

	@GetMapping("/piatto/delete/")
	public String deletePiattoAll( Model model) {
		piattoService.deleteAll();
		return"index";
	}


	//AGGIORNAMENTO

	@GetMapping("piatto/edit/{id}")
	public String showSceltaEditForm(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "piatto/piattoEditScelta";
	}

	@GetMapping("piatto/edit/edit/{id}")
	public String showPiattoEditForm(@PathVariable("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "piatto/piattoEdit";
	}

	@PostMapping("piatto/edit/edit/{id}")
	public String update( @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		String nomeFile = null;
		if(file != null)
			nomeFile =file.getOriginalFilename();

		if(nomeFile != null)
			piatto.setImmagine("..\\images\\chef\\" + nomeFile);
		else
			piatto.setImmagine("..\\images\\chef\\sample.jpg");

		if(!bindingResult.hasErrors()) {
			piattoService.update(piatto);
			return"piatto/piatto";
		}
		return"piatto/piattoForm";
	}


	@GetMapping("/piatto/edit/caricaImmagine/{id}")
	public String caricaImmagineFormEditShow(@PathVariable("id") Long id, Model model){
		model.addAttribute("immagine",new Object());
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("chef", piatto);
		return "chef/immagineForm";
	}

	@PostMapping("/piatto/edit/caricaImmagine/{id}")
	public String caricaImmagineEdit( @RequestParam("immagine") MultipartFile immagine, @PathVariable("id") Long id, Model model) {
		try {
			file = immagine;
			model.addAttribute("immagine",immagine);
			piattoService.saveImmagine(immagine);
			Piatto piatto = piattoService.findById(id);

			String nomeFile = null;
			if(file != null)
				nomeFile =file.getOriginalFilename();

			if(nomeFile != null)
				piatto.setImmagine("..\\images\\chef\\" + nomeFile);
			else
				piatto.setImmagine("..\\images\\chef\\sample.jpg");
			piattoService.update(piatto);
			return "piatto/immagineEditOk";
		} catch (IOException e) {
			return "piatto/immagineForm";
		}
	}


}
