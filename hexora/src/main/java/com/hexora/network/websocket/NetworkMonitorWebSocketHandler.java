package com.hexora.network.websocket;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

@Component
public class NetworkMonitorWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkMonitorWebSocketHandler.class);
    private final ObjectMapper objectMapper;
    private final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public NetworkMonitorWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        WebSocketSession safeSession = new ConcurrentWebSocketSessionDecorator(session, 10_000, 65_536);
        sessions.put(session.getId(), safeSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
    }

    public void publish(Object update) {
        final String json;
        try {
            json = objectMapper.writeValueAsString(update);
        } catch (JacksonException exception) {
            throw new IllegalStateException("Could not serialize network monitoring update", exception);
        }

        for (var entry : sessions.entrySet()) {
            try {
                entry.getValue().sendMessage(new TextMessage(json));
            } catch (IOException exception) {
                sessions.remove(entry.getKey());
                LOGGER.warn("Could not deliver network update to WebSocket session {}", entry.getKey(), exception);
            }
        }
    }
}
