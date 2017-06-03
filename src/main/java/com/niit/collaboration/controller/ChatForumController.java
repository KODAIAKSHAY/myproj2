package com.niit.collaboration.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.niit.collaboration.model.Message;
import com.niit.collaboration.model.OutputMessage;

@Controller
public class ChatForumController {
private static final Logger log = LoggerFactory.getLogger(ChatForumController.class);

@MessageMapping("chat_forum")
@SendTo("/topic/message")
public OutputMessage sendMessage(Message message){
	log.debug("Start of method sendMessage");
	log.debug("Message:"+message.getMessage());
	log.debug("MessageID:"+message.getId());

	return new OutputMessage(message,new Date());
}
}