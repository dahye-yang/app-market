package org.dadak.market.dao;

import java.util.List;
import java.util.Map;

import org.dadak.market.model.ChatMessage;
import org.dadak.market.model.ChatRoom;

public interface ChatDao {
	
	public abstract int saveChatRoom(ChatRoom one);
	
	public abstract ChatRoom findChatRoomyProductIdAndBuyerId(Map<String, Object> one);

	public abstract int saveChatMessage(ChatMessage one);
	
	public abstract ChatRoom findChatRoomById(String id);
	
	public abstract List<ChatRoom> findChatRoomListById(String logonAccount);
		
	public abstract List<ChatMessage> findChatmessageByRoomId(String id);
	
	public abstract List<ChatMessage> findAfterChatMessageByRoomId(Map<String,Object> one);
	
	public abstract int countUncheckedMessageByRoomId(Map<String, Object> one);
	
	public abstract int updateCheckAtByRoomId(Map<String, Object> one);
	
	
}
