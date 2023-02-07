package com.cateringservice.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cateringservice.demo.service.BuffetService;
import com.cateringservice.demo.service.ChefService;
import com.cateringservice.demo.service.PiattoService;

@Controller
public class CateringController {

	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService   chefService;	
	
	@Autowired
	private PiattoService piattoService;
	
	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("chefs", this.chefService.firstInsertedChef());
		model.addAttribute("buffets", this.buffetService.firstInsertedBuffet());
		model.addAttribute("piatti", this.piattoService.firstInsertedPlates());
		return "index";
	}
}
