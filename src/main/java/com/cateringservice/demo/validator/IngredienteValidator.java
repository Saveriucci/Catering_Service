package com.cateringservice.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cateringservice.demo.model.Ingrediente;
import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator{
	@Autowired 
	private IngredienteService ingredienteService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingrediente ingrediente = (Ingrediente) target;

		if(ingredienteService.existByNomeAndOrigine(ingrediente.getNome(), ingrediente.getOrigine())) {
			errors.reject("ingrediente.duplicato");
		}
		
		if( ingrediente.getNome().isBlank()) {
			errors.reject("ingrediente.nome");
		}
		
		if( ingrediente.getOrigine().isBlank()) {
			errors.reject("ingrediente.origine");
		}
		
		if( ingrediente.getDescrizione().isBlank()) {
			errors.reject("ingrediente.descrizione");
		}
	}

}

