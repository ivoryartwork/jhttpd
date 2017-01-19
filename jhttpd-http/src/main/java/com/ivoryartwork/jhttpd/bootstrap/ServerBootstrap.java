package com.ivoryartwork.jhttpd.bootstrap;

import com.ivoryartwork.jhttpd.handler.codec.http.HttpRequestDecoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 启动引导
 *
 * @author Yaochao
 * @version 1.0
 */
public class ServerBootstrap {

    public void start() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.socket().bind(new InetSocketAddress(9999));
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.interrupted()) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isAcceptable()) {
                    ServerSocketChannel sc = (ServerSocketChannel) sk.channel();
                    SocketChannel channel = sc.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    SocketChannel channel = (SocketChannel) sk.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    channel.configureBlocking(false);
                    int n;
                    while ((n = channel.read(buffer)) > 0) {
                        channel.read(buffer);
                        out.write(buffer.array());
                        buffer.clear();
                    }
                    HttpRequestDecoder requestDecoder = new HttpRequestDecoder();
                    requestDecoder.decode(out.toByteArray());
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("HTTP/1.1 200 OK\n");
                    stringBuffer.append("Content-Type: text/html;charset=utf-8\n");
                    stringBuffer.append("Content-Length: 11\n\n");
                    stringBuffer.append("hello world");
                    channel.write(ByteBuffer.wrap(stringBuffer.toString().getBytes()));
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.start();
    }
}
