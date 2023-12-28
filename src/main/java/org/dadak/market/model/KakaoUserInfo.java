package org.dadak.market.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoUserInfo {
	
	private Long id;
	
	@SerializedName("properties")
	private KakaoProfile profile;
}
