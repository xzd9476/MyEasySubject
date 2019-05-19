package com.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.ServletContext;

/**
 * Spring websocket������
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     * �������/websocketʱ����wensocket���ӣ����HttpSessionHandshakeInterceptor��Ϊ��websocket����ǰ��httpsession�е�����
     * ��ӵ�websocket session�У�withSockJS��Ӷ�sockJS��֧��
     *
     * @param stompEndpointRegistry
     */
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/websocket").addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
    }

    /**
     * ������Ϣ������/appΪͷ��url�����Ⱦ���MessageMapping
     * /topicֱ�ӽ�����Ϣ����
     *
     * @param registry
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
