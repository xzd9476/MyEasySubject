package com.web.websocket;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.user.enums.LoginTypeEnum;
import com.user.pojo.LoginInfo;
import com.user.pojo.ParticipantRepository;
import com.user.pojo.User;
import com.user.service.UserService;

/**
 * websocket�Ͽ����Ӵ�������SessionDisconnectEvent�¼������������߾�֪ͨ�����û�
 */
@Component
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private UserService userService;

    private final static String SUBSCRIBE_LOGOUT_URI = "/topic/logout";

    /**
     * ��sessionDisconnectEvent����ʱ���˷����������ã����¼��е�messageȡ��websocket sessionAttributes
     * ����ȡ���뿪��User���������û�map��ɾ�����û���֪ͨ�����û�
     * @param sessionDisconnectEvent
     */
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        Map<String, User> activeSessions = participantRepository.getActiveSessions();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        User disconnectSession = (User) sessionAttributes.get("user");
        String disconnectUserName = disconnectSession.getName();
        if (participantRepository.containsUserName(disconnectUserName)){
            User removeUser = participantRepository.remove(disconnectUserName);
            removeUser.setLogoutDate(new Date());
            //����ǳ���Ϣ
            User userByName = userService.getUserByName(removeUser.getName());
            LoginInfo loginInfo = LoginInfo.builder().userId(userByName == null ? null : userByName.getId())
                    .userName(removeUser.getName()).
                    status(LoginTypeEnum.LOGOUT.getCode()).createTime(new Date()).build();
            userService.addUserLoginInfo(loginInfo);
            logger.info(removeUser.getLogoutDate() + ", " + removeUser.getName() + " logout.");
            messagingTemplate.convertAndSend(SUBSCRIBE_LOGOUT_URI, removeUser);
        }
    }
}
