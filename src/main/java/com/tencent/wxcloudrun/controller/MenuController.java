package com.tencent.wxcloudrun.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
	
	private static RestTemplate restTemplate;
	
	private static String getRequestEntity(String url) {
		ResponseEntity<String> entity=restTemplate.getForEntity(url, String.class);
		return entity.getBody();
	}	
	
	@GetMapping("/create")
	public static String createMenu() {
		String url="https://api.weixin.qq.com/cgi-bin/menu/create";
		
		return getRequestEntity(url);
	}
}
