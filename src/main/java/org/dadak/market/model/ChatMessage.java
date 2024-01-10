package org.dadak.market.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	private int id;
	private String chatRoomId;
	private String talkerId;
	private String content;
	private Date sentAt;
	private Date checkedAt;
	
	private String strSentAt;
}
