package org.dadak.market.service;

import java.net.URI;

import org.dadak.market.model.KakaoUserInfo;
import org.dadak.market.model.KakaoOauthToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class KakaoAPIService {

	public KakaoOauthToken getOauthTokenCode(String code) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", "23952a54f84ff853b8e5cda55c83230e");
		body.add("redirect_uri", "http://192.168.4.13:8080/market/callback/kakao");
		body.add("code", code);
		
		var request = new RequestEntity<>(body, headers, HttpMethod.POST, 
											URI.create("https://kauth.kakao.com/oauth/token"));
		// -- 여기까지 사용자 정보 접근 권한을 GET한 상황
		RestTemplate template = new RestTemplate();
		
		var response =  template.exchange(request, String.class);
		
		//System.out.println(response.getBody());
		Gson gson= new Gson();
		return gson.fromJson(response.getBody(),KakaoOauthToken.class);
		// accesscode를 get한 상황

	}
	
	public KakaoUserInfo getUserInfo(String accessToken) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer "+accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		var request = new RequestEntity<>(headers, HttpMethod.GET, 
											URI.create("https://kapi.kakao.com/v2/user/me"));
		// -- 여기까지 사용자 정보 접근 권한을 GET한 상황
		RestTemplate template = new RestTemplate();
		
		var response =  template.exchange(request, String.class);
		
		Gson gson= new Gson();
		
		return gson.fromJson(response.getBody(), KakaoUserInfo.class);
	}
}
