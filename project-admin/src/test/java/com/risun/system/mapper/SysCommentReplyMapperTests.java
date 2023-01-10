package com.risun.system.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.risun.RisunApplicationTests;
import com.risun.system.domain.SysCommentReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysCommentReplyMapperTests extends RisunApplicationTests {

	@Autowired
	private SysCommentReplyMapper sysCommentReplyMapper;
	
	@Test
	public void selectSysCommentReplyByCommentIds() {
		List<SysCommentReply> list = sysCommentReplyMapper.selectSysCommentReplyByCommentIds(Lists.newArrayList(10L, 20L, 30L));
		log.info("{}", list);
	}
	
}
