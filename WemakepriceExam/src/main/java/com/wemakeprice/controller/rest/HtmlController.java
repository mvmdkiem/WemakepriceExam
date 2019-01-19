package com.wemakeprice.controller.rest;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wemakeprice.model.request.HomeReqeust;
import com.wemakeprice.service.HtmlService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *
 * @version     1.00 
 * @author      김태형
 * @since       JDK 10
 */
@Slf4j
@RestController
public class HtmlController {
	static final Logger logger = LoggerFactory.getLogger(HtmlController.class);
	
	@Autowired
	HtmlService service;
	
	/**
     * request param 테스트
     * @param   
     * @return  HTML 내용을 가져오는 Service를 호출
	 * @throws IOException 
     */
	@RequestMapping(value="/htmls/read", method=RequestMethod.GET)
	public Map<String, Object> search(HomeReqeust req) throws IOException {
		logger.info("----------------------");
		logger.info("req.param()" + req.getUrl());
		logger.info("req.param()" + req.getPrintCnt());
		logger.info("req.param()" + req.getType());
		logger.info("----------------------");
		return service.search(req); 
	}
	
	
	 /**
     * rest API 테스트
     * @param   
     * @return  페이지 네비게이션 관련 정보
     */
//	@RequestMapping(value="/api/test/read", method=RequestMethod.GET)
//	public Map<String, Object> search(HomeReqeust test) {
//		return service.search(test); 
//	}
	
	
}
