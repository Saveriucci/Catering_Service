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
		String string = utente.getPassword();
		int count = 0;
		for( int i = 0; i < string.length(); i++){
			count++;
		}
		
		if(count < 5 ) {
			errors.reject("utente.password");
		}
		
		if(utente.getNome().isBlank()) {
			errors.reject("utente.nome");
		}
		
		if(utente.getCognome().isBlank()) {
			errors.reject("utente.cognome");
		}
		
		if(utente.getUsername().isBlank()) {
			errors.reject("utente.username");
		}
	}


}
