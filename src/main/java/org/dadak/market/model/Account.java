package org.dadak.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	private String id;
	private String nickname;
	private String platform;
	private String accessToken;
	private String profileImageUrl;
	private String address;
	private Double latitude;
	private Double longitude;
}
