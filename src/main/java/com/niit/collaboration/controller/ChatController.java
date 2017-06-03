package com.niit.collaboration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.niit.collaboration.model.Message;

@Controller
public class ChatController {
	private static final Logger log = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat")
	@SendTo("/queue/message")
	public void sendMessage(Message message){
		log.debug("Start of method sendMessage");
		log.debug("Message:"+message.getMessage());
		System.out.println("--------------------------------------------------------------------");
		System.out.println("------------------------------hi-----------------------------------");
		System.out.println("---------------------------------------------------------------------");
		
		simpMessagingTemplate.convertAndSend("/queue/message/",message);
	}
	
}
