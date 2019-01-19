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

	//String urlstr = "https://www.naver.com/";
	
	private List<String> list = new ArrayList<String>();
	private String str = "";
	
	@Override
	public Map<String, Object> search(HomeReqeust req) throws IOException {
		this.str = "";
		this.list = new ArrayList<String>();
		
		Map<String, Object> result = new HashMap<String, Object>();
		//result.put("html", getHtml(req.getUrl()));
		//map.put("test2", getHtmlByJsoup());
		
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
		
		//log.info(list);
		result.put("result", list);
		//log.info(String.valueOf(Character.toChars(111)));
		//log.info("map1 : " + map1);
		//log.info("map2 : " + map2);
		//log.info("map3 : " + map3);
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
		
		//log.info(list);
		//log.info(String.valueOf(Character.toChars(111)));
	}
	
//	private List<String> getStrList(Map<String, Map<Integer, Integer>> map, int printCnt){
//		log.info("test!");
//		
//		Map<Integer, Integer> uMap = map.get("uMap");
//		Map<Integer, Integer> sMap = map.get("sMap");
//		Map<Integer, Integer> nMap = map.get("nMap");
//		
//		while(!uMap.isEmpty() || !sMap.isEmpty() || !nMap.isEmpty()) {
//			if(!uMap.isEmpty()) {
//				Integer key = Collections.min(uMap.keySet());
//				str += String.valueOf(Character.toChars(key));
//				if(uMap.get(key) == 1) {
//					uMap.remove(key);
//				} else {
//					uMap.put(key, uMap.get(key) - 1);
//				}
//			}
//			
//			if(!sMap.isEmpty()) {
//				Integer key = Collections.min(sMap.keySet());
//				str += String.valueOf(Character.toChars(key));
//				if(sMap.get(key) == 1) {
//					sMap.remove(key);
//				} else {
//					sMap.put(key, sMap.get(key) - 1);
//				}
//			}
//			
//			if(!nMap.isEmpty()) {
//				Integer key = Collections.min(nMap.keySet());
//				str += String.valueOf(Character.toChars(key));
//				if(nMap.get(key) == 1) {
//					nMap.remove(key);
//				} else {
//					nMap.put(key, nMap.get(key) - 1);
//				}
//			}
//			
//			log.info("str : " + str);
//			list.add(str);
//			str = "";
//		}
//		
//		log.info(list);
//		log.info(String.valueOf(Character.toChars(111)));
//
//		return list;
//	}
	
	private List<String> getHtml(String urlPath) {
		List<String> result = new ArrayList<String>();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("getHtml START");

        String pageContents = "";
        //StringBuilder contents = new StringBuilder();
 
        try{
 
            URL url = new URL(urlPath);
            URLConnection con = (URLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader (con.getInputStream(), "UTF-8");
 
            BufferedReader buff = new BufferedReader(reader);
 
            while((pageContents = buff.readLine())!=null){
            	result.add(pageContents);
                //contents.append(pageContents);
                //contents.append("\r\n");
            }
 
            buff.close();
 
            //log.info(contents.toString());
 
        }catch(Exception e){
            e.printStackTrace();
        }
        
		stopWatch.stop();
		logger.info(stopWatch.getLastTaskTimeMillis() + " ms");
        return result;
	}
	
	private Map<String, Map<Integer, Integer>> getAsciiMapOutterTag(List<String> strs){
		Map<String, Map<Integer, Integer>> resultMap = new HashMap<String, Map<Integer, Integer>>();
		Map<Integer, Integer> uMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> sMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> nMap = new HashMap<Integer, Integer>();
		
		Stack<Integer> tag = new Stack<Integer>();
		for(String str: strs) {
		    //log.info(str);
		    char[] chars = str.toCharArray();
		    for(char ch: chars) {
		    	int code = (int) ch;
		    	if(code == 60) {
		    		//log.info("push");
		    		tag.push(code);
		    	} else if(code == 62){
		    		//log.info("pop!");
		    		tag.pop();
		    	}
		    	
		    	if(tag.isEmpty()) {
		    		//log.info(code);
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
		    //log.info(str);
		    char[] chars = str.toCharArray();
		    for(char ch: chars) {
		    	int code = (int) ch;
		    	
	    		//log.info(code);
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
	
	
	
	private String getHtmlByJsoup() throws IOException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("getHtmlByJsoup START");
		
		Document doc = Jsoup.connect("https://www.naver.com/").get();
		logger.info(doc.data());
		//logger.info(doc.title());
//		Elements newsHeadlines = doc.select("#mp-itn b a");
//		for (Element headline : newsHeadlines) {
//			logger.info("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
//		}
		
		stopWatch.stop();
		logger.info(stopWatch.getLastTaskTimeMillis() + " ms");
		return doc.body().text(); // "An example link"

	}
	
}
