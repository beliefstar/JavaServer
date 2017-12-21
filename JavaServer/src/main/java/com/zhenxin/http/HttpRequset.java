package com.zhenxin.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequset {
    private String uri;
    private String method;
    private String protocol;

    private Map<String, String> Header;
    private Map<String, String> QueryString;
    private Map<String, String> Form;
    private Map<String, String> Parameter;

    public HttpRequset(InputStream in) {
    	Form = new HashMap<String, String>();
        Header = new HashMap<String, String>();
        Parameter = new HashMap<String, String>();
        QueryString = new HashMap<String, String>();
        analysis(in);
    }

    private void analysis(InputStream in) {
        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            String line = buffer.readLine();
            if (line != null && line.length() > 0) {
                String[] temp = line.split("\\s");
                this.method = temp[0];
                this.uri = temp[1];
                if (this.uri.indexOf("?") != -1) {
                    String str = this.uri.substring(this.uri.indexOf("?") + 1);
                    genarenal(str, false);
                    this.uri = this.uri.substring(0, this.uri.indexOf("?"));
                }
                if (this.uri.endsWith("/")) {
                    this.uri += "index.html";
                }
                this.protocol = temp[2];
            }
            System.out.println(method + " " + uri + " " + protocol);
            String l = null;
            int len = 0;
            while ((l = buffer.readLine()) != null) {
                if ("".equals(l)) {
                    break;
                }
                String k = l.substring(0, l.indexOf(":")).trim();
                String v = l.substring(l.indexOf(":") + 1).trim();
                this.Header.put(k, v);
                
                if (l.indexOf("Content-Length") != -1) {
                    len = Integer.parseInt(l.substring(l.indexOf(":") + 1).trim());
                }
            }
            if (method != null && method.toUpperCase().equals("POST")) {
                char[] bys = new char[len];
                buffer.read(bys);
                String paraStr = new String(bys);
                genarenal(paraStr, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void genarenal(String str, boolean isPost) {
        String[] arr = str.split("&");
        for (String s : arr) {
            String[] temp = s.split("=");
            if (isPost) {
                this.Form.put(temp[0], temp[1]);
            } else {
                this.QueryString.put(temp[0], temp[1]);
            }
            this.Parameter.put(temp[0], temp[1]);
        }
    }
    
    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String Header(String param) {
        return Header.get(param);
    }
    
    public Map<String, String> getHeader() {
        return Header;
    }

    public String QueryString(String param) {
        return QueryString.get(param);
    }

    public String Form(String param) {
        return Form.get(param);
    }

    public String Parameter(String param) {
        return Parameter.get(param);
    }
}
