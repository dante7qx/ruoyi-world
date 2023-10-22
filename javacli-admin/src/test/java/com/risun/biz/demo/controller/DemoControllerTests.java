package com.spirit.biz.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson2.JSON;
import com.spirit.RisunApplicationTests;
import com.spirit.biz.demo.domain.Demo;
import com.spirit.biz.demo.service.IDemoService;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoControllerTests extends RisunApplicationTests {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private DemoController demoController;
	@Mock
    private IDemoService demoService;

	Demo demo = null;
	
	String token = "eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6Ijg3NjM0ZTYyLWZmOTQtNDBiMi05NDRhLTgzNmI1YTU3NDY1ZCJ9.cZPoyDRrICZ_7ruEL3MwmDZYmAfnNdtGK8P1FoYQJUv50OmzHntiTWbYjzz3kjg34mRvRYnw3ATHQsMVL6emPQ";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
		
		demo = new Demo();	
		demo.setDemoName("Mock数据");
		demo.setDemoTime(DateUtil.date());
		demo.setRoleId(1L);
		demo.setDemoContent("Mock数据内容");
	}

	@Test
	void add() throws Exception {
		
		Mockito.when(demoService.insertDemo(demo)).thenReturn(1);
		
		MvcResult reult = mockMvc.perform(post("/biz/demo/insert")
				.header("Authorization", "Bearer ".concat(token))
				.characterEncoding(StandardCharsets.UTF_8)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(demo)))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		
		log.info(reult.getResponse().getContentAsString());
	}
	
}
