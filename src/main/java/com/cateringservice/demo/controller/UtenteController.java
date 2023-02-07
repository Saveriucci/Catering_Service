package com.cateringservice.demo.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.service.UtenteService;
import com.cateringservice.demo.validator.UtenteValidator;




@Controller
public class UtenteController {

	@Autowired private UtenteService utenteService;
	@Autowired private UtenteValidator utenteValidator;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new Utente());
		return "auth/registrationForm";
	}


	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") Utente user,
			BindingResult userBindingResult, Model model) {

		// validazione user
		utenteValidator.validate(user, userBindingResult);
		if(!userBindingResult.hasErrors()) {
			model.addAttribute("user", user);
			utenteService.saveUtente(user);
			return "auth/loginForm";
		}
		return "auth/registrationForm";
	}


	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "auth/loginForm";
	}


	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
	/*	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		//		if(credentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
		//			return "redirect:/";
		//		}*/
		return "redirect:/";

	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "redirect:/";
	}
}
