package com.zhenxin.common;

import com.zhenxin.http.HttpRequset;
import com.zhenxin.http.HttpResponse;

public interface Action {
	void bridge(HttpRequset req, HttpResponse res);
}
