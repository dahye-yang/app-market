package org.dadak.market.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverOauthToken {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private int expires_in;
	
}
