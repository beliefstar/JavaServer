package com.zhenxin.core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.zhenxin.common.ServerContext;
import com.zhenxin.http.HttpRequset;
import com.zhenxin.http.HttpResponse;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket s) {
        socket = s;
    }
    
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            HttpRequset req = new HttpRequset(in);
            
            if (req.getUri() != null) {
                String type = req.getUri().substring(req.getUri().lastIndexOf(".") + 1);
                HttpResponse res = new HttpResponse(out);
                res.setHeader("Content-Type", ServerContext.getType(type));
                
                File file = new File(ServerContext.WebRoot + req.getUri());
                
                if (!file.exists()) {
                    res.setStatus(404);
                    file = new File(ServerContext.WebRoot + "/" + ServerContext.NotFoundPage);
                } else {
                    res.setStatus(200);
                }
                
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                byte[] bys = new byte[(int) file.length()];
                bis.read(bys);
                
                res.write(bys);
                
                bis.close();
                socket.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
