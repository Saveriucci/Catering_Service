package com.cateringservice.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cateringservice.demo.model.Utente;
import com.cateringservice.demo.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void saveUtente(Utente utente) {
		if (utente.getRole() == null) {
			utente.setRole(Utente.DEFAULT_ROLE);
		}
		utente.setPassword(passwordEncoder.encode(utente.getPassword()));
		utenteRepository.save(utente);
	}
	
	public boolean existsByUsername( Utente utente) {
		return utenteRepository.existsByUsername(utente.getUsername());
	}
	
	
	public Utente getUser(Long id) {
		return utenteRepository.findById(id).get();
	}
}
