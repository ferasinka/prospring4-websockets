package com.ferasinka.prospring4websockets;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class EchoHandler extends TextWebSocketHandler {
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		session.sendMessage(new TextMessage(message.getPayload()));
	}
}
