package com.spirit.framework.websocket;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * 消息信息
 * 
 * @author dante
 *
 */
@Data
public class WebSocketMsg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 
	 * 消息标题
	 */
	private String title;
	
	/**
	 * 消息内容 
	 */
	private String content;
	
	/**
	 * 额外信息
	 */
	private Map<String, Object> extra;
	
	public void addExtra(String key, Object val) {
		if(CollUtil.isEmpty(extra)) {
			this.extra = Maps.newHashMap();
		}
		this.extra.put(key, val);
	}

}
