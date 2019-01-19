package com.wemakeprice.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.wemakeprice.model.request.HomeReqeust;
import com.wemakeprice.service.HtmlService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("HtmlService")
public class HtmlServiceImpl implements HtmlService{
	static final Logger logger = LoggerFactory.getLogger(HtmlServiceImpl.class);
	
	private List<String> list = new ArrayList<String>();
	private String str = "";
	
	@Override
	public Map<String, Object> search(HomeReqeust req) throws IOException {
		this.str = "";
		this.list = new ArrayList<String>();
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Map<Integer, Integer>> charMap = new HashMap<String, Map<Integer, Integer>>();
		if("txt".equals(req.getType())) {
			charMap = getAsciiMapOutterTag(getHtml(req.getUrl()));
		} else if("html".equals(req.getType())){
			charMap = getAsciiMapByTag(getHtml(req.getUrl()));
		}
		
		Map<Integer, Integer> uMap = charMap.get("uMap");
		Map<Integer, Integer> sMap = charMap.get("sMap");
		Map<Integer, Integer> nMap = charMap.get("nMap");
		
		while(!uMap.isEmpty() || !sMap.isEmpty() || !nMap.isEmpty()) {
			if(!uMap.isEmpty()) {
				getStrList(uMap, req.getPrintCnt());
			}
			
			if(!sMap.isEmpty()) {
				getStrList(sMap, req.getPrintCnt());
			}
			
			if(!nMap.isEmpty()) {
				getStrList(nMap, req.getPrintCnt());
			}
		}
		if(!str.equals("")) list.add(str);
		
		result.put("result", list);
		return result;
	}
	
	private void getStrList(Map<Integer, Integer> map, int printCnt){
		Integer key = Collections.min(map.keySet());
		str += String.valueOf(Character.toChars(key));
		if(map.get(key) == 1) {
			map.remove(key);
		} else {
			map.put(key, map.get(key) - 1);
		}
		
		if(str.length() >= printCnt) {
			list.add(str);
			str = "";
		}
	}
	
	private List<String> getHtml(String urlPath) {
		List<String> result = new ArrayList<String>();

		String pageContents = "";
        try{
 
            URL url = new URL(urlPath);
            URLConnection con = (URLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader (con.getInputStream(), "UTF-8");
 
            BufferedReader buff = new BufferedReader(reader);
 
            while((pageContents = buff.readLine())!=null){
            	result.add(pageContents);
            }
 
            buff.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
	}
	
	private Map<String, Map<Integer, Integer>> getAsciiMapOutterTag(List<String> strs){
		Map<String, Map<Integer, Integer>> resultMap = new HashMap<String, Map<Integer, Integer>>();
		Map<Integer, Integer> uMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> sMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> nMap = new HashMap<Integer, Integer>();
		
		Stack<Integer> tag = new Stack<Integer>();
		for(String str: strs) {
		    char[] chars = str.toCharArray();
		    for(char ch: chars) {
		    	int code = (int) ch;
		    	if(code == 60) {
		    		tag.push(code);
		    	} else if(code == 62){
		    		tag.pop();
		    	}
		    	
		    	if(tag.isEmpty()) {
			    	if(code >= 65 && code <= 90) {
			    		if(uMap.containsKey(code)) {
			    			uMap.put(code, uMap.get(code) + 1);
			    		} else {
			    			uMap.put(code, 1);
			    		}
			    	} else if(code >= 97 && code <= 122) {
			    		if(sMap.containsKey(code)) {
			    			sMap.put(code, sMap.get(code) + 1);
			    		} else {
			    			sMap.put(code, 1);
			    		}
			    	} else if(code >= 48 && code <= 57) {
			    		if(nMap.containsKey(code)) {
			    			nMap.put(code, nMap.get(code) + 1);
			    		} else {
			    			nMap.put(code, 1);
			    		}
			    	}
		    	}
		    }
		}
		
		resultMap.put("uMap", uMap);
		resultMap.put("sMap", sMap);
		resultMap.put("nMap", nMap);
		return resultMap;
	}
	
	private Map<String, Map<Integer, Integer>> getAsciiMapByTag(List<String> strs){
		Map<String, Map<Integer, Integer>> resultMap = new HashMap<String, Map<Integer, Integer>>();
		Map<Integer, Integer> uMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> sMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> nMap = new HashMap<Integer, Integer>();
		
		for(String str: strs) {
		    char[] chars = str.toCharArray();
		    for(char ch: chars) {
		    	int code = (int) ch;
		    	
		    	if(code >= 65 && code <= 90) {
		    		if(uMap.containsKey(code)) {
		    			uMap.put(code, uMap.get(code) + 1);
		    		} else {
		    			uMap.put(code, 1);
		    		}
		    	} else if(code >= 97 && code <= 122) {
		    		if(sMap.containsKey(code)) {
		    			sMap.put(code, sMap.get(code) + 1);
		    		} else {
		    			sMap.put(code, 1);
		    		}
		    	} else if(code >= 48 && code <= 57) {
		    		if(nMap.containsKey(code)) {
		    			nMap.put(code, nMap.get(code) + 1);
		    		} else {
		    			nMap.put(code, 1);
		    		}
		    	}
		    }
		}
		
		resultMap.put("uMap", uMap);
		resultMap.put("sMap", sMap);
		resultMap.put("nMap", nMap);
		return resultMap;
	}
}
