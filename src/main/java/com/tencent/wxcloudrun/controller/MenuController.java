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
	
	@GetMapping("/create")
	public static String createMenu() {
		String url="https://api.weixin.qq.com/cgi-bin/menu/create";
		log.info("create menu:{}",url);
		return getRequestEntity(url);
	}
	@PostMapping("/msg")
	public static String getMessage() {
		String url="https://api.weixin.qq.com/customservice/msgrecord/getmsglist";
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
}
