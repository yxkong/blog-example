package com.yxkong.netty;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yxkong
 * @Date: 2021/8/17 9:54 上午
 * @version: 1.0
 */
public class NioServer {
    Selector selector = null;
    ThreadPoolExecutor pool = new ThreadPoolExecutor(5,10,30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(200));

    /**
     * 初始化通道，并对通道做一些参数设置
     * @param port
     * @throws IOException
     */
    public void initServer(String host,int port) throws IOException{
        //1，开启一个channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(Boolean.FALSE);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName(host),port));
        //打开一个多路复用器
        this.selector = Selector.open();
        //将多路复用器绑定到channel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }
    public void listener() throws IOException{
        //轮训访问select
        for (;;){
            //当注册的事件到达时，继续执行，否则会一直阻塞
            selector.select();
            /**
             * selector.keys() 与 selector.selectedKeys()
             *  keys 返回的是不可修改的
             *  selectedKeys 返回的是可以修改删除的
             */
            selector.selectedKeys().forEach(key->{
                handler(key);
            });
        }
    }

    private void handler(SelectionKey key) {
        try {
            if (key.isAcceptable()){
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                SocketChannel client = channel.accept();
                client.configureBlocking(false);
                client.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ,byteBuffer.duplicate());
                System.out.println(" 5ycode accepted connection from "+ client);
            }
            if (key.isWritable()){
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                while (buffer.hasRemaining()){
                    if (client.write(buffer)==0){
                        break;
                    }
                }
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            key.cancel();
            try {
                key.channel().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void server(int port) throws IOException{
        final ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        final ServerSocket socket = socketChannel.socket();
        final InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        socket.bind(inetSocketAddress);
        final Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer byteBuffer = ByteBuffer.wrap("welcome to 5ycode ".getBytes(StandardCharsets.UTF_8));
        for (;;){
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.stream().forEach(k->{
                try {
                    if (k.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel)k.channel();
                        SocketChannel client = channel.accept();
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ,byteBuffer.duplicate());
                        System.out.println(" 5ycode accepted connection from "+ client);
                    }
                    if (k.isWritable()){
                        SocketChannel client = (SocketChannel) k.channel();
                        ByteBuffer buffer = (ByteBuffer) k.attachment();
                        while (buffer.hasRemaining()){
                            if (client.write(buffer)==0){
                                break;
                            }
                        }
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    k.cancel();
                    try {
                        k.channel().close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        try {
            nioServer.server(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}