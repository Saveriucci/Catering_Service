package com.cateringservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cateringservice.demo.model.Piatto;
import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.service.PiattoService;

@Component
public class PiattoValidator implements Validator{
	@Autowired PiattoService piattoService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Piatto piatto = (Piatto) target;

		if(piattoService.existsByNomePiatto(piatto.getNomePiatto())) {
			errors.reject("piatto.duplicato");
		}
	}

}
