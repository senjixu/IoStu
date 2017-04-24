package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
	
	public static void main(String[] args) throws IOException{
		//打开一个socket server channel
		ServerSocketChannel server = ServerSocketChannel.open();
		
		//绑定8800端口
		server.socket().bind(new InetSocketAddress(8800));
		//设置非足赛
		server.configureBlocking(false);
		//获得通道管理器
		Selector selector = Selector.open();
		//注册监听事件
		server.register(selector, SelectionKey.OP_ACCEPT);
		//轮询selector
		while(true){
			
			//accept事件到达之前会一直阻塞
			selector.select();
			
			//获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator<?> iterator = selector.selectedKeys().iterator();
			while(iterator.hasNext()){
				SelectionKey key = (SelectionKey) iterator.next();
				iterator.remove();
				
				//用户链接事件
				if(key.isAcceptable()){
					ServerSocketChannel sv = (ServerSocketChannel)key.channel();
					//获得与客户端的通道
					SocketChannel channel = sv.accept();
					
					channel.configureBlocking(false);
					System.out.println("new connection");
					channel.register(selector, key.OP_READ);
				}else if(key.isReadable()){
					//可读消息
					SocketChannel channel = (SocketChannel)key.channel();
					
					ByteBuffer by = ByteBuffer.allocate(1024);
					
					int read = channel.read(by);
					
					if(read > 0){
						byte[] data = by.array();
						
						String msg = new String(data).trim();
						System.out.println("get msg:" + msg);
						
						ByteBuffer out = ByteBuffer.wrap("ok".getBytes());
						channel.write(out);
					}
					
					System.out.println("close connection");
					
					key.cancel();
					
					
				}
			}
		}
	}
	
}
