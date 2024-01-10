package org.dadak.market.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pick {
	private int id;
	private String ownerAccountId;
	private int targetProductId;
	
	private Product product;
}
