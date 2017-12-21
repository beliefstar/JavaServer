package com.zhenxin.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zhenxin.common.ServerContext;

public class WebServer {
    private ServerSocket server;
    private ExecutorService threadPool;
    public WebServer() {
        try {
            server = new ServerSocket(ServerContext.Port);
            threadPool = Executors.newFixedThreadPool(ServerContext.MaxThread);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败！");
        }
    }
    
    public void start() {
        try {
            while (true) {
                System.out.println("等待客户端连接");
                Socket socket = server.accept();
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (Exception e) {
        }
    }
    
    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
    }
}
