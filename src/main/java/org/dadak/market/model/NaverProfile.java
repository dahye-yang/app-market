package org.dadak.market.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class NaverProfile {
	private String id;
	private String nickname;
	
	@SerializedName("profile_image")
	private String profileImage;
	
}
