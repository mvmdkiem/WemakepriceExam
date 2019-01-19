package com.wemakeprice.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.wemakeprice.service.HtmlService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestServiceImpl.class})
public class TestServiceImpl {
	@Autowired
    private TestServiceImpl service;
	
	@Autowired
    private RestTemplate restTemplate;



//	@Test
//	public void serviceTest() throws Exception {
//		
//		//this.mockMvc.perform(get("/api/test/read"))
//		//.andDo(print())
//		//.andExpect(status().isOk()).andExpect(model().attributeExists("type"));
//
//	}

}
