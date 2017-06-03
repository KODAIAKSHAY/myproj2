package com.niit.collaboration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("com.niit")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer 
{
private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

@Override
public void configureMessageBroker(MessageBrokerRegistry config){
	log.debug("Start of method to configureMessageBroker");
	config.enableSimpleBroker("/topic","/queue");
	config.setApplicationDestinationPrefixes("/app");
	log.debug("End of method configureMessageBroker");
}
public void registerStompEndpoints(StompEndpointRegistry registry) {
	log.debug("Start of method registerStompEndpoints");
	registry.addEndpoint("/chat").withSockJS();
	registry.addEndpoint("/chat_forum").withSockJS();
	log.debug("End of method registerStompEndpoints");
}
}
