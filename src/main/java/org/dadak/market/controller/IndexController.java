package org.dadak.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dadak.market.dao.ProductDao;
import org.dadak.market.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexController {
	
	public final ProductDao productDao;
	
	@GetMapping({ "/", "/index" })
	public String showWelcome(@RequestParam (defaultValue = "1") int p 
								,@RequestParam(defaultValue = "") String search
								,Model model) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("start", (p - 1) * 6 + 1);
		map.put("end", p * 6);
		
		String[] words = search.split("\\s+");
		map.put("words", words);
		
		List<Product> list = productDao.findSomeByPaging(map);
		model.addAttribute("list", list);
		
		int cntProduct = productDao.countProduct(map);
		int lastPage = cntProduct / 6 + (cntProduct % 6 > 0 ? 1 : 0);
		
		model.addAttribute("end", lastPage);
		model.addAttribute("currentPage", p);
		return "index";
	}
}
