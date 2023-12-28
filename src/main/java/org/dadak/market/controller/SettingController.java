package org.dadak.market.controller;

import javax.servlet.http.HttpSession;

import org.dadak.market.dao.AccountDao;
import org.dadak.market.model.Account;
import org.dadak.market.model.GetAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class SettingController {
	
	private final AccountDao accounDao;
	
	@GetMapping({"/settings","/settings/profile"})
	public String showSettings(HttpSession session,Model model) {
		Account one = (Account)session.getAttribute("logonAccount");
		if(one != null) {
			Account found = accounDao.findByAccountId(one.getId());
			if(found != null) {
				model.addAttribute("found", found);
			}
		}
		return "settings/form";
	}
	
	@PostMapping({"/settings","/settings/profile"})
	public String changeProfile(@ModelAttribute GetAddress getAdderss ,HttpSession session, Model model) {
		//System.out.println(getAdderss.toString());
		Account found = (Account)session.getAttribute("logonAccount");
		Account one = new Account();
		one.setId(found.getId());
		one.setAddress(getAdderss.getAddress());
		one.setLatitude(getAdderss.getLatitude());
		one.setLongitude(getAdderss.getLongitude());
		//accounDao.findByAccountId(found.getId());
		int x = accounDao.update(one);
		
		//System.out.println("업데이트 결과 --> "+x);
		return "redirect:/settings";
	}
}
