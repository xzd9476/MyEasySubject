package com.user.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.curator.framework.recipes.leader.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.enums.LoginTypeEnum;
import com.user.pojo.LoginInfo;
import com.user.pojo.ParticipantRepository;
import com.user.pojo.ReplyLoginMessage;
import com.user.pojo.ReplyRegistMessage;
import com.user.pojo.User;
import com.user.service.UserService;

@Controller
public class LoginAction {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("userservice")
	private UserService userservice;
	//private SimpMessagingTemplate messageingTemplate;//消息模板
	@Autowired
	private SimpMessagingTemplate messageingTemplate;
	@Autowired
	private ParticipantRepository participantRepository;//在线用户存储
	private static final String SUBSCRIBE_LOGIN_URL="/topic/login";//订阅的登录地址

	/*
	 * 用于反馈ajax登录信息
	 */
	@RequestMapping(value = "/Login/login", method = RequestMethod.POST)
	@ResponseBody
	public ReplyLoginMessage ReplyLoginMessage(@RequestBody User user) {
		if (user.getName() == null || user.getName().trim().equals("") || user.getPassword() == null
				|| user.getPassword().trim().equals("")) {
			return new ReplyLoginMessage(false, ReplyLoginMessage.USER_NAME_OR_PASWORD_NULL);
		}
		boolean isExit = userservice.isExistUser(user.getName());
		if (!isExit) {
			return new ReplyLoginMessage(false, ReplyLoginMessage.USER_NAME_NOT_EXIST);
		}
		boolean passok = userservice.checkpassword(user.getName(), user.getPassword());
		if (!passok) {
			return new ReplyLoginMessage(false, ReplyLoginMessage.USER_PASSWORD_WRONG);
		}
		return new ReplyLoginMessage(true);
	}

	/*
	 * 用于反馈ajax注册的信息
	 */
	@RequestMapping(value = "/Login/regist", method = RequestMethod.POST)
	@ResponseBody
	public ReplyRegistMessage doregist(@RequestBody User user) {
		boolean isExit = userservice.isExistUser(user.getName());
		if (isExit) {
			return new ReplyRegistMessage(false, ReplyRegistMessage.USER_NAME_EXTST);
		}
		if (user.getPassword() != null) {
			userservice.doregist(user.getName(), user.getPassword());
		}
		return new ReplyRegistMessage(true);
	}

	/*
	 * 登录成功进入聊天室
	 */
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public String loginIntoChatRoom(User user, HttpServletRequest request) {
		user = userservice.getUserByName(user.getName());
		if (user==null) {
			return "index";
		}
		user.setPassword(null);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		// 保存用户登录信息
		LoginInfo loginInfo = LoginInfo.builder().userId(user.getId()).userName(user.getName())
				.status(LoginTypeEnum.LOGIN.getCode()).createTime(new Date()).build();
		userservice.addUserLoginInfo(loginInfo);
		messageingTemplate.convertAndSend(SUBSCRIBE_LOGIN_URL,user);
		participantRepository.add(user.getName(), user);
		logger.info(user.getLoginDate()+","+user.getName()+" login.");
		return "chatroom";
	}
	
	@SubscribeMapping("/chat/participants")
	public Long getActiveUserNumber(){
		return Long.valueOf(participantRepository.getActiveSessions().values().size());
	}

}
