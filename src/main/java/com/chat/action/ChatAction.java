package com.chat.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chat.pojo.Message;
import com.chat.pojo.MessageRecord;
import com.user.enums.MessageTypeEnum;
import com.user.pojo.User;
import com.user.service.UserService;

@Controller
public class ChatAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String SUBSCRIBE_MESSAGE_URL = "/topic/chat/message";// 订阅接收消息的地址
	private static final String IMAGE_PREFIX = "/resources/media/image/"; // 服务器储存上传图片地址的前缀

	@Autowired
	private SimpMessagingTemplate messaingTemplate;
	@Autowired
	private UserService userService;

	/*
	 * 接受并且转发消息
	 */
	@MessageMapping("/chat/message")
	public void receiveMessage(Message message) {
		message.setSendDate(new Date());
		message.setMessageType("text");
		logger.info(message.getSendDate() + "," + message.getUserName() + "send a message:" + message.getContent());

		User userByName = userService.getUserByName(message.getUserName());
		MessageRecord messageRecordDo = MessageRecord.messageRecordBuilder()
				.userId(userByName == null ? null : userByName.getId()).userName(message.getUserName())
				.content(message.getContent()).messageType(MessageTypeEnum.TEXT.getCode()).createTime(new Date())
				.build();
		userService.addUserMessageRecord(messageRecordDo);
		messaingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URL, message);
	}

	/**
	 * 接收转发图片
	 * 
	 * @param request
	 * @param imageFile
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/upload/image", method = RequestMethod.POST)
	@ResponseBody
	public String handleUploadImage(HttpServletRequest request, @RequestParam("image") MultipartFile imageFile,
			@RequestParam("userName") String userName) {
		if (!imageFile.isEmpty()) {
			String imageName = userName + "_" + (int) (Math.random() * 1000000) + ".jpg";
			String path = request.getSession().getServletContext().getRealPath(IMAGE_PREFIX) + "/" + imageName;
			File localImageFile = new File(path);
			try {
				// 上传图片到目录
				byte[] bytes = imageFile.getBytes();
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						new FileOutputStream(localImageFile));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
				Message message = new Message();
				message.setMessageType("image");
				message.setUserName(userName);
				message.setSendDate(new Date());
				message.setContent(request.getContextPath() + IMAGE_PREFIX + imageName);

				// 保存发送图片信息
				User userByName = userService.getUserByName(message.getUserName());
				MessageRecord messageRecordDo = MessageRecord.messageRecordBuilder()
						.userId(userByName == null ? null : userByName.getId()).userName(userName)
						.content(message.getContent()).messageType(MessageTypeEnum.IMAGE.getCode())
						.createTime(new Date()).build();
				userService.addUserMessageRecord(messageRecordDo);

				messaingTemplate.convertAndSend(SUBSCRIBE_MESSAGE_URL, message);
			} catch (IOException e) {
				logger.error("图片上传失败：" + e.getMessage(), e);
				return "upload false";
			}
		}
		return "upload success";
	}
}
