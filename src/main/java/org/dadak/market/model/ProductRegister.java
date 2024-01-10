package org.dadak.market.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductRegister {
	private int id;
	private MultipartFile[] images;
	private String title;
	private String type;
	private int price;
	private String description;
}
