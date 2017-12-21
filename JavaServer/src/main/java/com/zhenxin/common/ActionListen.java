package com.zhenxin.common;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zhenxin.http.HttpRequset;
import com.zhenxin.http.HttpResponse;


public class ActionListen {
	private static Map<String, String> GetMethod;
	private static Map<String, String> PostMethod;
	
	static {
		loadListenes();
	}
	
	public static void doAction(HttpRequset req, HttpResponse res, String methodurl, boolean isPost) {
		try {
			String target = null;
			if (isPost) {
				target = PostMethod.get(methodurl);
			} else {
				target = GetMethod.get(methodurl);
			}
			Class<? extends Object> cls = Class.forName(target);
			Object obj = cls.newInstance();
			Method[] methods = cls.getDeclaredMethods();
			for (Method method : methods) {
				if ("bridge".equals(method.getName())) {
					method.invoke(obj, req, res);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadListenes() {
		GetMethod = new HashMap<String, String>();
		PostMethod = new HashMap<String, String>();
		try {
			SAXReader reader = new SAXReader();
			File file = new File("config/action.xml");
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements("listen");
			for (Element e : list) {
				Element action = e.element("action");
				if ("POST".equals(action.attributeValue("method").toUpperCase())) {
					PostMethod.put(action.getText(), e.elementText("target"));
				} else {
					GetMethod.put(action.getText(), e.elementText("target"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean isAction(String str, boolean isPost) {
		if (isPost) {
			return PostMethod.containsKey(str);
		} else {
			return GetMethod.containsKey(str);
		}
	}
}
