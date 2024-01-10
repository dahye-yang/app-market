package org.dadak.market.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInfo {
	private String nickname;
	private MultipartFile profileImage;
}
