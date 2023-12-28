package org.dadak.market.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NaverUserInfo {
	
	@SerializedName("response")
	private NaverProfile naverProfile;
	
}
