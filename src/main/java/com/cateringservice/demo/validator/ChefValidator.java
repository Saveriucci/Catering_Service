package com.cateringservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cateringservice.demo.model.Chef;
import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.service.ChefService;

@Component
public class ChefValidator implements Validator{
	
	@Autowired ChefService chefService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chef chef = (Chef) target;
		
		if(chefService.existsByNomeChefAndCognomeChefAndNazionalita(chef.getNomeChef(), chef.getCognomeChef(), chef.getNazionalita())) {
			errors.reject("chef.duplicato");
		}
		
		if(chef.getNomeChef().isBlank()) {
			errors.reject("chef.nome");
		}
		
		if(chef.getCognomeChef().isBlank()) {
			errors.reject("chef.cognome");
		}
		
		if(chef.getNazionalita().isBlank()) {
			errors.reject("chef.nazionalita");
		}
	}

}
