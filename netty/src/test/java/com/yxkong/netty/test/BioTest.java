package com.yxkong.netty.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Author: yxkong
 * @Date: 2021/8/17 1:28 下午
 * @version: 1.0
 */
public class BioTest {

    @Test
    public void bioTest(){
        int num = 100;
        String host = "127.0.0.1";
        int port = 8081;
        Socket  socket = null;
        for (int i = 0; i < 100; i++) {
            try {
                socket = new Socket(host,port);
                System.out.println(String.format("第%d次链接", i));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = br.readLine();
                System.out.println(msg);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void NioTest(){
        int num = 100;
        String host = "127.0.0.1";
        int port = 8082;
        Socket  socket = null;
        for (int i = 0; i < 100; i++) {
            try {
                socket = new Socket(host,port);
                System.out.println(String.format("第%d次链接", i));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = br.readLine();
                System.out.println(msg);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}