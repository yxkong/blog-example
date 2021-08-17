package com.yxkong.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @Author: yxkong
 * @Date: 2021/8/17 9:54 上午
 * @version: 1.0
 */
public class NioServer {
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
                        final SocketChannel client = channel.accept();
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