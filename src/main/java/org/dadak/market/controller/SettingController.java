package org.dadak.market.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.dadak.market.dao.AccountDao;
import org.dadak.market.model.Account;
import org.dadak.market.model.GetAddress;
import org.dadak.market.model.UpdateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
	// 프로필 변경
	@PostMapping("/settings/profile/info")
	public String changeNickAndProfileImage(@ModelAttribute UpdateInfo updateInfo
							,@SessionAttribute Account logonAccount,HttpSession session) throws IllegalStateException, IOException {
		
		//System.out.println("nickname----> "+ updateInfo.getNickname());
		//System.out.println("profileImage----> "+ updateInfo.getProfileImage());
		Account one = Account.builder().id(logonAccount.getId()).nickname(updateInfo.getNickname()).build();
		
		if(!updateInfo.getProfileImage().isEmpty()) {
			File dir = new File("c:\\upload\\profileImage\\", logonAccount.getId());
			dir.mkdirs();
			File target = new File(dir, "img.jpg");
			updateInfo.getProfileImage().transferTo(target);	
			one.setProfileImageUrl("/upload/profileImage/"+logonAccount.getId()+"/img.jpg");
		}
		
		accounDao.update(one);
		session.setAttribute("logonAccount", accounDao.findByAccountId(logonAccount.getId()));
		return "redirect:/settings/profile";
	}
	
	
	@PutMapping({"/settings","/settings/profile"})
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
