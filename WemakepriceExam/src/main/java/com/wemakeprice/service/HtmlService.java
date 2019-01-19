package com.wemakeprice.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wemakeprice.model.request.HomeReqeust;

@Service("HtmlService")
public interface HtmlService {
	public Map<String, Object> search(HomeReqeust req) throws IOException;
}
