package org.dadak.market.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dadak.market.dao.ChatDao;
import org.dadak.market.dao.ProductDao;
import org.dadak.market.model.Account;
import org.dadak.market.model.ChatMessage;
import org.dadak.market.model.ChatRoom;
import org.dadak.market.model.ChatRoomComplex;
import org.dadak.market.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
	
	private final ChatDao chatDao;
	private final ProductDao productDao;
	
	
	@GetMapping("/room/{id}")
	public String showChatRoom(@SessionAttribute Account logonAccount,
										@PathVariable String id, Model model) {
		
		
		ChatRoom found = chatDao.findChatRoomById(id);
		if(found ==null || (!logonAccount.getId().equals(found.getSellerId()) 
				&& !logonAccount.getId().equals(found.getBuyerId()))) {
			return "chat/error";
		}
		
		Product one = productDao.findById(found.getProductId());
		
	
		List<ChatMessage> messages = chatDao.findChatmessageByRoomId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("chatRoomId", id);
		map.put("talkerId", logonAccount.getId());
		chatDao.updateCheckAtByRoomId(map);
	
		model.addAttribute("product", one);
		model.addAttribute("chatRoom", found);
		model.addAttribute("chatMessages", messages);
		
		return "chat/room";
	}
	//  특정 상품의 채팅방에 연결 요청을 처리할 핸들러
	@GetMapping("/link")
	public String LinkChatRoom(@SessionAttribute Account logonAccount 
									,@RequestParam int productId, Model model) {
		System.out.println("productId---> "+productId);
		
		Product found = productDao.findById(productId);
		if(found.getAccountId().equals(logonAccount.getId())){
			return "chat/error";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("buyerId", logonAccount.getId());
		
		
		ChatRoom chatRoom = chatDao.findChatRoomyProductIdAndBuyerId(map);
		if(chatRoom != null) {
			return "redirect:/chat/room/"+chatRoom.getId();
		}else {
			String uuid = UUID.randomUUID().toString();
			String[] uuids = uuid.split("-");
			
			ChatRoom x = ChatRoom.builder().id(uuids[4].toUpperCase())//
											.productId(productId)//
											.sellerId(found.getAccountId())//
											.buyerId(logonAccount.getId())//
											.build();//
			chatDao.saveChatRoom(x);
			return "redirect:/chat/room/"+x.getId();
		}
		
	}
		
	@GetMapping("/chatlist")
	public String showCahtList(@SessionAttribute Account logonAccount, Model model) {
		List<ChatRoomComplex> complex = new ArrayList<>();
		List<ChatRoom> chatRoomList = chatDao.findChatRoomListById(logonAccount.getId());
		for(ChatRoom one : chatRoomList) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("chatRoomId", one.getId());
			map.put("talkerId", logonAccount.getId());
			int y = chatDao.countUncheckedMessageByRoomId(map);
			List<ChatMessage> messages = chatDao.findChatmessageByRoomId(one.getId());
			ChatRoomComplex x = new ChatRoomComplex();
			if(messages == null || messages.isEmpty()) {
				x.setRecentMessage(null);
			}else {
				x.setRecentMessage(messages.get(messages.size()-1).getContent());	
			}
			//System.out.println("messages의 사이즈----> "+messages.size());
			Product product = productDao.findById(one.getProductId());
			x.setProduct(product);
			x.setChatRoom(one);
			x.setUnread(y);
			
			complex.add(x);
		
		}
		
		model.addAttribute("complex", complex);
		return "chat/chatlist";
	}
	
	//메세지 등록처리하는 핸들러 매핑
	@PostMapping("/room/{id}/message")
	@ResponseBody
	public String proceedAddChatMessage(@SessionAttribute Account logonAccount
										,@PathVariable String id,@RequestParam String content
										, Model model) {

		// 값을 받아오는 방법(pathbariable, requestParam)
		ChatMessage messages = ChatMessage.builder().chatRoomId(id)
													.talkerId(logonAccount.getId())
													.content(content).build();
		
		chatDao.saveChatMessage(messages);
		
		Gson gson = new Gson();
		Map<String, Object> response = new HashMap<>();
		response.put("result", "success");
		
		
		return gson.toJson(response);
	}
	
	// 특정 시점이후의 메시지 목록을 전송하는 핸들러
	@ResponseBody
	@GetMapping(
			path ="/room/{chatRoomId}/latest",
			produces = "text/plain;charset=utf-8"
			)
	public String proceedFindLatesMessage(@PathVariable String chatRoomId,
											@RequestParam int lastMessageId 
											,@SessionAttribute Account logonAccount,Model model) {
		
		Map<String,Object> criteria = new HashMap<String, Object>();
		criteria.put("chatRoomId", chatRoomId);
		criteria.put("lastMessageId", lastMessageId);
		
		List<ChatMessage> list = chatDao.findAfterChatMessageByRoomId(criteria);
		DateFormat dateFormat = new SimpleDateFormat("a hh:mm");
		for(ChatMessage one : list) {
			one.setStrSentAt(dateFormat.format(one.getSentAt()));
		}
		
		// 맵 클리어, 새롭게셋팅한 후 이걸 기준으로 호출
		criteria.clear();
		criteria.put("chatRoomId", chatRoomId);
		criteria.put("talkerId", logonAccount.getId());
		
		chatDao.updateCheckAtByRoomId(criteria);
		
		Map<String,Object> response = new HashMap<>();
		response.put("result", list.size());
		response.put("messages", list);
		
		Gson gson = new Gson();
		
		return gson.toJson(response);
		
	}
	
	
}
