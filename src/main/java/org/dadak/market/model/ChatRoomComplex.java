package org.dadak.market.model;

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
public class ChatRoomComplex {
	private ChatRoom chatRoom;
	private Product product;
	private String recentMessage;
	private int unread;
}
