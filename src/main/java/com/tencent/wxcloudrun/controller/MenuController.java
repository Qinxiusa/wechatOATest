package com.tencent.wxcloudrun.controller;

import java.util.HashMap;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class MenuController {
	
	private static RestTemplate restTemplate;
	
	private static String getRequestEntity(String url) {
		if(restTemplate==null)
			restTemplate=new RestTemplate();
		
		ResponseEntity<String> entity=restTemplate.getForEntity(url, String.class);
		
		log.info("response:{}",entity.toString());
		
		return entity.getBody();
	}	
	//资源复用时，需要额外携带调用方的AppId，否则就会报错：api unauthorized rid: 62e34bba-083ae8bb-5b9f65c1
	private static String encodeAppID(String url) {
		return url+"?from_appid=wxd443973223af430d";
	}
	private static String responseSuccess() {
		return "Success";
	}
	@GetMapping("/create")
	public static String createMenu() {
		String url="https://api.weixin.qq.com/cgi-bin/menu/create";
		url=encodeAppID(url);
		log.info("create menu:{}",url);
		return getRequestEntity(url);
	}
	//在此接收微信传递过来的消息，需要在云托管->设置->全局设置中告知微信我们项目接收消息的入口
	@PostMapping("/recv")
	@ResponseBody
	public static String onMessageEvent(@RequestBody String body) {
		
		try {
			JSONObject jsonBody=new JSONObject(body);
			
			if(jsonBody.optString("action")!=null) {
				if(jsonBody.optString("action").contentEquals("CheckContainerPath")) {
					log.info("check container path ok!");
				}				
			}else {
				log.info("receive wechat msg:{}",jsonBody);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.info("decode body error:",e.getMessage());
		}
		
		return responseSuccess();
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
	
	@GetMapping("/getip")
	public static String getwechatIp() {
		String url="https://api.weixin.qq.com/cgi-bin/get_api_domain_ip";
		String iplist=getRequestEntity(url);
		try {
			JSONObject jsondata=new JSONObject(iplist);
			
			
			JSONArray jsonarray=jsondata.optJSONArray("ip_list");
			
			iplist=jsonarray.toString();
			
		} catch (JSONException e) {
			log.info("getip error:{}",e.getMessage());
		}
		
		return iplist;
	}
	@GetMapping("/getcallbackip")
	public static String getCallbackIp() {
		String url="https://api.weixin.qq.com/cgi-bin/getcallbackip";
		String iplist=getRequestEntity(url);
		try {
			JSONObject jsondata=new JSONObject(iplist);
			
			
			JSONArray jsonarray=jsondata.optJSONArray("ip_list");
			
			iplist=jsonarray.toString();
			
		} catch (JSONException e) {
			log.info("getcallbackip error:{}",e.getMessage());
		}
		
		return iplist;		
	}
}
