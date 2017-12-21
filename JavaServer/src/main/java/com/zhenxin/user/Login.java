package com.zhenxin.user;

import com.zhenxin.common.Action;
import com.zhenxin.http.HttpRequset;
import com.zhenxin.http.HttpResponse;

public class Login implements Action{

	public void bridge(HttpRequset req, HttpResponse res) {
		String user = req.Form("user");
		String pwd = req.Form("password");
		String rel = user + "--" + pwd;
		res.write(rel);
		
	}
	
}
