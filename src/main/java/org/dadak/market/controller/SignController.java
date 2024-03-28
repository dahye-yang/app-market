package org.dadak.market.controller;

import javax.servlet.http.HttpSession;

import org.dadak.market.dao.AccountDao;
import org.dadak.market.model.Account;
import org.dadak.market.model.KakaoOauthToken;
import org.dadak.market.model.KakaoUserInfo;
import org.dadak.market.model.NaverOauthToken;
import org.dadak.market.model.NaverUserInfo;
import org.dadak.market.service.KakaoAPIService;
import org.dadak.market.service.NaverAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class SignController {

	private final KakaoAPIService kakaoAPIService;
	private final NaverAPIService naverAPIService;
	private final AccountDao accountDao;
	
	@Value("${kakao.client.id}")
	String kakaoClientId;
	@Value("${kakao.redirect.uri}")
	String kakaoRedirectUri;

	@Value("${naver.client.id}")
	String naverClientId;
	@Value("${naver.redirect.uri}")
	String naverRedirectUri;
	
	@GetMapping("/signin")
	public String showSign(Model model) {

		// 카카오
		String kakaoLoginLink = "https://kauth.kakao.com/oauth/authorize?";
		kakaoLoginLink += "client_id=${client_id}&response_type=code";
		kakaoLoginLink += "&redirect_uri=${redirect_uri}";

//		String redirectURL = "http://192.168.4.13:8080${contextPath}/callback/kakao".replace("${contextPath}",
//				application.getContextPath());

		kakaoLoginLink = kakaoLoginLink.replace("${client_id}", kakaoClientId);
		kakaoLoginLink = kakaoLoginLink.replace("${redirect_uri}", kakaoRedirectUri);
		
		// 네이버
		String naverLoginLink = "https://nid.naver.com/oauth2.0/authorize?";
		naverLoginLink  += "client_id=${client_id}&response_type=code&";
		naverLoginLink += "redirect_uri=${redirect_uri}";
		
		naverLoginLink = naverLoginLink.replace("${client_id}", naverClientId);
		naverLoginLink = naverLoginLink.replace("${redirect_uri}", naverRedirectUri);
		
		model.addAttribute("naverLoginLink", naverLoginLink);
		model.addAttribute("kakaoLoginLink", kakaoLoginLink);

		return "signin";
	}

	@GetMapping("/callback/kakao")
	public String acceptCode(@RequestParam String code, HttpSession session) {
		// 억세스 토근 받아오기 (카카오로부터)
		KakaoOauthToken oauthToken = kakaoAPIService.getOauthTokenCode(code);
		// System.out.println("oauthToken--->> "+oauthToken.getAccess_token());
		// User 정보 가져오기
		KakaoUserInfo kakaoUserInfo = kakaoAPIService.getUserInfo(oauthToken.getAccess_token());
		// System.out.println("kakaoUserInfo---> "+kakaoUserInfo);

		// 등록된 아이디가 있는지 체크
		// 없을 경우 DB저장
		Account found = accountDao.findByAccountId("K" + kakaoUserInfo.getId());
		String id = "K" + kakaoUserInfo.getId();
		if (found == null) {
			Account one = Account.builder().id(id).nickname(kakaoUserInfo.getProfile().getNickname()).platform("kakao")
					.accessToken(oauthToken.getAccess_token())
					.profileImageUrl(kakaoUserInfo.getProfile().getProfileImage()).build();
			accountDao.saveAccount(one);
			session.setAttribute("logonAccount", one);

		} else {
			// 있을 경우 update
			found.setAccessToken(oauthToken.getAccess_token());
			// found.setNickname(kakaoUserInfo.getProfile().getNickname());
			// found.setProfileImageUrl(kakaoUserInfo.getProfile().getProfileImage());

			accountDao.update(found);
			session.setAttribute("logonAccount", found);
		}
		return "redirect:/index";
	}

	@GetMapping("/callback/naver")
	public String acceptCodeForNaver(@RequestParam String code, HttpSession session) {
		// 억세스 토근 받아오기 (네이버)
		// System.out.println("네이버 코드-->> "+code);
		NaverOauthToken oauthToken = naverAPIService.getOauthTokenCodeAtNaver(code);
		// System.out.println("oauthToken--->> "+oauthToken.getAccess_token());
		// User 정보 가져오기
		NaverUserInfo naverUserInfo = naverAPIService.getUserInfo(oauthToken.getAccess_token());
		// System.out.println("naverUserInfo---> "+naverUserInfo);

		// 등록된 아이디가 있는지 체크
		// 없을 경우 DB저장
		Account found = accountDao.findByAccountId(naverUserInfo.getNaverProfile().getId());
		String id = naverUserInfo.getNaverProfile().getId();
		if (found == null) {
			Account one = Account.builder().id(id).nickname(naverUserInfo.getNaverProfile().getNickname()).platform("naver")
					.accessToken(oauthToken.getAccess_token())
					.profileImageUrl(naverUserInfo.getNaverProfile().getProfileImage()).build();
			accountDao.saveAccount(one);
			session.setAttribute("logonAccount", one);

		} else {
			// 있을 경우 update
			found.setAccessToken(oauthToken.getAccess_token());
			// found.setNickname(kakaoUserInfo.getProfile().getNickname());
			// found.setProfileImageUrl(kakaoUserInfo.getProfile().getProfileImage());

			accountDao.update(found);
			session.setAttribute("logonAccount", found);
		}
		return "redirect:/index";

	}

	@GetMapping("/signout")
	public String logoutHandel(HttpSession session) {
		session.invalidate();

		return "redirect:/index";
	}

}
