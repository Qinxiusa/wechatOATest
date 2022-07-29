package com.tencent.wxcloudrun.controller;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/menu")
@Slf4j
public class MenuController {
	
	private static RestTemplate restTemplate;
	
	private static String getRequestEntity(String url) {
		if(restTemplate==null)
			restTemplate=new RestTemplate();
		
		ResponseEntity<String> entity=restTemplate.getForEntity(url, String.class);
		return entity.getBody();
	}	
	//资源复用时，需要额外携带调用方的AppId，否则就会报错：api unauthorized rid: 62e34bba-083ae8bb-5b9f65c1
	private static String encodeAppID(String url) {
		return url+"?from_appid=wxd443973223af430d";
	}
	@GetMapping("/create")
	public static String createMenu() {
		String url="https://api.weixin.qq.com/cgi-bin/menu/create";
		url=encodeAppID(url);
		log.info("create menu:{}",url);
		return getRequestEntity(url);
	}
	@PostMapping("/msg")
	public static String getMessage() {
		String url="https://api.weixin.qq.com/customservice/msgrecord/getmsglist";
		url=encodeAppID(url);
		log.info("get msg:{}",url);
		
//      设置请求头，请求类型为json
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

//      设置请求参数
      HashMap<String, Object> map = new HashMap<>();
      map.put("starttime", 987654321);
      map.put("endtime",987654321);
      map.put("msgid",1);
      map.put("number",100);

      //用HttpEntity封装整个请求报文
      HttpEntity<HashMap<String, Object>> request = new HttpEntity<>(map, headers);

		if(restTemplate==null)
			restTemplate=new RestTemplate();
      
		return restTemplate.postForObject(url, request,String.class);	
	}
	@GetMapping("/test")
	public static String feedbackMsg() {
		log.info("test : do nothing");
		return "test ok!";
	}
	
	@GetMapping("/token")
	public static String getAccessToken() {
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxd443973223af430d&secret=54b2add49dc1aed04ae09a0927b27bd2";
		log.info("get access token:{}",url);
		return getRequestEntity(url);
	}	
	
}
