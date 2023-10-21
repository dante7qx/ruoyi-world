package com.risun.framework.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket 消息处理
 * 
 * @author sunchao
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/message/{userName}")
public class WebSocketServer {

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param userName 当前登录用户
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("userName") String userName) throws Exception {
		if(StrUtil.isEmpty(userName)) {
			log.info("未登录系统！");
			session.close();
		} else {
			// 添加用户
			WebSocketClient.put(userName, session);
			// 获取当前用户的待办信息，发送消息给用户
			/*
			WebSocketMsg msg = new WebSocketMsg();
			msg.setTitle("待办信息");
			msg.setContent("你有一条待办信息待处理！");
			msg.addExtra("url", "/demo/test");
			WebSocketClient.sendMessageToUser(userName, msg);
			*/
		}
	}

	/**
	 * 连接关闭时处理
	 */
	@OnClose
	public void onClose(Session session) {
		log.info("关闭连接 - {}", session);
		// 移除用户
		WebSocketClient.remove(session);
	}

	/**
	 * 抛出异常时处理
	 */
	@OnError
	public void onError(Session session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			// 关闭连接
			session.close();
		}
		String sessionId = session.getId();
		log.info("连接异常 - {}", sessionId);
		log.info("异常信息 - {}", exception);
		// 移出用户
		WebSocketClient.remove(session);
	}

	/**
	 * 服务器接收到客户端消息时调用的方法
	 * 
	 * @throws IOException 
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		String userName = WebSocketClient.getUserName(session);
		if(StrUtil.isEmpty(userName)) {
			log.info("未登录系统！");
			session.close();
		} else {
			WebSocketMsg msg = new WebSocketMsg();
			msg.setTitle(message);
			msg.setContent(message);
			WebSocketClient.sendMessageToUser(userName, msg);
		}
		
	}
}
