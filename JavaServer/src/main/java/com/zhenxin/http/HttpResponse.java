package com.zhenxin.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zhenxin.common.HttpContext;
import com.zhenxin.common.ServerContext;

public class HttpResponse {
    
    private Map<Integer, String> Status;
    
    private Map<String, String> Header;
    
    private int status;
    
    private OutputStream out;
    
    public HttpResponse(OutputStream out) {
        this.out = out;
        
        Header = new LinkedHashMap<String, String>();
        Status = new HashMap<Integer, String>();
        
        Status.put(HttpContext.STATUS_CODE_OK, HttpContext.STATUS_REASON_OK);
        Status.put(HttpContext.STATUS_CODE_NOT_FOUND, HttpContext.STATUS_REASON_NOT_FOUND);
        Status.put(HttpContext.STATUS_CODE_ERROR, HttpContext.STATUS_REASON_ERROR);
        
        Header.put("Content-Type", "text/plain;charset=utf-8");
        Header.put("Date", new Date().toString());
        status = 200;
    }

    public void write(byte[] bys) {
        Header.put("Content-Length", Integer.toString(bys.length));
        
        PrintStream ps = new PrintStream(out);
        printHeader(ps);
        try {
            ps.write(bys);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ps.flush();
    }

    public void write(String str) {
        Header.put("Content-Length", Integer.toString(str.length()));
        
        PrintStream ps = new PrintStream(out);
        printHeader(ps);
        
        ps.println(str);
        
        ps.flush();
    }
    
    private void printHeader(PrintStream ps) {
    	ps.println(ServerContext.Protocol + " " + status + " " + Status.get(status));
        Set<Entry<String, String>> set = Header.entrySet();
        for (Entry<String, String> entry : set) {
            String k = entry.getKey();
            String v = entry.getValue();
            ps.println(k + ":" + v);
        }
        ps.println("");
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public void setHeader(String key, String value) {
        Header.put(key, value);
    }
    
}
