package com.cateringservice.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cateringservice.demo.model.Buffet;
import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.service.BuffetService;

@Component
public class BuffetValidator implements Validator{

	@Autowired BuffetService buffetService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Buffet buffet = (Buffet) target;
		
		if(buffetService.existsByNomebuffet(buffet.getNomeBuffet())) {
			errors.reject("buffet.duplicato");
		}
	}


}
