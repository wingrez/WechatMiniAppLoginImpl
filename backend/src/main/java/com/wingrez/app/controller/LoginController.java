package com.wingrez.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wingrez.app.common.HttpClientUtil;
import com.wingrez.app.common.JsonResult;
import com.wingrez.app.common.JsonUtils;
import com.wingrez.app.common.RedisOperator;
import com.wingrez.app.model.SessionModel;

@RestController
public class LoginController {
	
	@Autowired
	private RedisOperator redis;
	
	@PostMapping("/login")
	public JsonResult login(String code) {
		System.out.println("code: "+code);
		
		String url="https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param=new HashMap<>();
		param.put("appid", "wx76227144645f14df");
		param.put("secret", "61e5515e9a2f65fd93493c707f7f8791");
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		
		String wxResult=HttpClientUtil.doGet(url, param);
		System.out.println(wxResult);
		
		SessionModel model=JsonUtils.jsonToPojo(wxResult, SessionModel.class);
		//存入redis
		redis.set("user-redis-session:"+model.getOpenid(), model.getSession_key(), 1000*60*30);
		
		return JsonResult.ok();
	}
}
