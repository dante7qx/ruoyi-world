package com.spirit.framework.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * websocket 客户端用户集
 * 
 * @author sunchao
 */
@Slf4j
public class WebSocketClient {

	/**
	 * 用户集
	 */
	private static Map<String, Session> USERS = new ConcurrentHashMap<String, Session>();

	/**
	 * 存储用户
	 *
	 * @param userName 用户名
	 * @param session 用户信息
	 */
	public static void put(String userName, Session session) {
		USERS.put(userName, session);
	}
	
	/**
	 * 获取用户名
	 * 
	 * @param session
	 * @return
	 */
	public static String getUserName(Session session) {
		String key = null;
		boolean flag = USERS.containsValue(session);
		if (flag) {
			Set<Map.Entry<String, Session>> entries = USERS.entrySet();
			for (Map.Entry<String, Session> entry : entries) {
				Session value = entry.getValue();
				if (value.equals(session)) {
					key = entry.getKey();
					break;
				}
			}
		}
		return key;
	}

	/**
	 * 移除用户
	 *
	 * @param session 用户信息
	 *
	 * @return 移除结果
	 */
	public static boolean remove(Session session) {
		String key = null;
		boolean flag = USERS.containsValue(session);
		if (flag) {
			Set<Map.Entry<String, Session>> entries = USERS.entrySet();
			for (Map.Entry<String, Session> entry : entries) {
				Session value = entry.getValue();
				if (value.equals(session)) {
					key = entry.getKey();
					break;
				}
			}
		} else {
			return true;
		}
		return remove(key);
	}

	/**
	 * 移出用户
	 *
	 * @param key 键
	 */
	public static boolean remove(String key) {
		Session remove = USERS.remove(key);
		if (remove != null) {
			boolean containsValue = USERS.containsValue(remove);
			return containsValue;
		} else {
			return true;
		}
	}

	/**
	 * 获取在线用户列表
	 *
	 * @return 返回用户集合
	 */
	public static Map<String, Session> getUsers() {
		return USERS;
	}

	/**
	 * 群发消息文本消息
	 *
	 * @param message 消息内容
	 */
	public static void sendMessageToAll(WebSocketMsg message) {
		Set<String> keys = USERS.keySet();
		for (String userName : keys) {
			sendMessageToUser(userName, message);
		}
	}

	/**
	 * 发送文本消息
	 *
	 * @param userName 用户名
	 * @param message  消息内容
	 */
	public static void sendMessageToUser(String userName, WebSocketMsg message) {
		Session session = USERS.get(userName);
		if (session != null) {
			try {
				session.getBasicRemote().sendText(JSON.toJSONString(message));
			} catch (Exception e) {
				log.error("[你已离线]", e);
			}
		} else {
			log.info("[{}已离线]", userName);
		}
	}
}
