package com.eoe.osori.global.advice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
    private static final String WEB_SOCKET_HOST = "http://localhost:*";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/chat/sub");  // 메시지 구독 요청 prefix
        config.setApplicationDestinationPrefixes("/chat/pub");   // 메시지 발행 요청 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp websocket endpoint 설정( ws://localhost:8080/chat/ws-stomp )
        registry.addEndpoint("/chat/ws-stomp").setAllowedOriginPatterns(WEB_SOCKET_HOST).withSockJS();
    }
}
