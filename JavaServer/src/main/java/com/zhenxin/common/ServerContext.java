package com.zhenxin.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ServerContext {
    public static String Protocol;
    public static int Port;
    public static int MaxThread;
    public static String WebRoot;
    public static String NotFoundPage;
    private static Map<String, String> Types;
    
    static {
        init();
    }
    
    private static void init() {
        Types = new HashMap<String, String>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read("config/config.xml");
            Element root = doc.getRootElement();
            
            Element service = root.element("service");
            Element connector = service.element("connector");
            Protocol = connector.attributeValue("protocol");
            Port = Integer.parseInt(connector.attributeValue("port"));
            MaxThread = Integer.parseInt(connector.attributeValue("max-thread"));
            WebRoot = service.elementText("webroot");
            NotFoundPage = service.elementText("not-found-page");
            
            @SuppressWarnings("unchecked")
            List<Element> typeMappings = root.element("type-mappings").elements("type-mapping");
            for (Element e : typeMappings) {
                Types.put(e.attributeValue("ext"), e.attributeValue("type"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getType(String ext) {
        return Types.get(ext);
    }
}
