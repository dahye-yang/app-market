package org.dadak.market.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetAddress {
	private String address;
	private Double latitude;
	private Double longitude;
}
