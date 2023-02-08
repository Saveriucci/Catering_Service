package com.cateringservice.demo.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.service.UtenteService;


@Component
public class UtenteValidator implements Validator{

	@Autowired private UtenteService utenteService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Utente utente = (Utente) target;
		if(utenteService.existsByUsername(utente)) {
			errors.reject("utente.duplicato");
		}
		
	}


}
