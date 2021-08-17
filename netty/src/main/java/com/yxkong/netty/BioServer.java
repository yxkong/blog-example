package com.yxkong.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * bio server
 *
 * @Author: yxkong
 * @Date: 2021/8/17 9:40 上午
 * @version: 1.0
 */
public class BioServer {

    public void server(int port)throws IOException {
        //建立socket通道
        final ServerSocket socket = new ServerSocket(port,10, InetAddress.getByName("127.0.0.1"));
        try {
            //死循环监听请求
            for (;;){
                //获取请求
                final Socket accept = socket.accept();
                //处理请求
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out = null;
                        try {
                            out = accept.getOutputStream();
                            //输出返回结果
                            out.write(String.format("hello %s:%d welcome to 5ycode",accept.getInetAddress(),accept.getPort() ).getBytes(StandardCharsets.UTF_8));
                            out.flush();
                            //关闭连接
                            accept.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                accept.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        try {
            bioServer.server(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}