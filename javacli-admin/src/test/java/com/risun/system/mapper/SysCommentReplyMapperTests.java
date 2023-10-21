package com.spirit.system.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.spirit.RisunApplicationTests;
import com.spirit.system.domain.SysCommentReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SysCommentReplyMapperTests extends RisunApplicationTests {

	@Autowired
	private SysCommentReplyMapper sysCommentReplyMapper;
	
	@Test
	void selectSysCommentReplyByCommentIds() {
		List<SysCommentReply> list = sysCommentReplyMapper.selectSysCommentReplyByCommentIds(Lists.newArrayList(10L, 20L, 30L));
		log.info("{}", list);
	}
	
}
