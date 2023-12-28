package org.dadak.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping({ "/", "/index" })
	public String showWelcome() {
		return "index";
	}
}
