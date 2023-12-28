package org.dadak.market.service;

import java.net.URI;

import org.dadak.market.model.KakaoUserInfo;
import org.dadak.market.model.NaverOauthToken;
import org.dadak.market.model.NaverUserInfo;
import org.dadak.market.model.KakaoOauthToken;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
public class NaverAPIService {

	public NaverOauthToken getOauthTokenCodeAtNaver(String code) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-type", "application/x-www-form-urlencoded");
		
		//String tokenURL = "https://nid.naver.com/oauth2.0/token";
		String client_id = "lHVLEjFOMfMNSlJeg5dj";
		String client_secret = "5dgvSriNBv";
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", client_id);
		body.add("client_secret", client_secret);
		body.add("code", code);
		
		var request = new RequestEntity<>(body,headers, HttpMethod.POST, 
											URI.create("https://nid.naver.com/oauth2.0/token"));
		// -- 여기까지 사용자 정보 접근 권한을 GET한 상황
		RestTemplate template = new RestTemplate();
		
		var response =  template.exchange(request, String.class);
		
		System.out.println("네이버에서 넘어온 응답데이터-> "+response.getBody());
		Gson gson= new Gson();
		return gson.fromJson(response.getBody(),NaverOauthToken.class);
		// accesscode를 get한 상황
		
	}
	
	public NaverUserInfo getUserInfo(String accessToken) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer "+accessToken);
		
		var request = new RequestEntity<>(headers, HttpMethod.GET, 
											URI.create("https://openapi.naver.com/v1/nid/me"));
		// -- 여기까지 사용자 정보 접근 권한을 GET한 상황
		RestTemplate template = new RestTemplate();
		
		var response =  template.exchange(request, String.class);
		
		Gson gson= new Gson();
		
		return gson.fromJson(response.getBody(), NaverUserInfo.class);
	}
}
