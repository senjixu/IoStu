package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client {

	private Selector selector;
	public static void main(String[] args) throws IOException{
		Client client = new Client();
		client.initClinet("127.0.0.1", 8800);
		client.listen();
	}
	
	public void initClinet(String ip,int port) throws IOException{
		//获得一个sock通道
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		
		this.selector = Selector.open();
		
		socketChannel.connect(new InetSocketAddress(ip,port));
		
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
	}
	
	public void listen() throws IOException{
		
		while(true){
			selector.select();
			
			Iterator it = selector.selectedKeys().iterator();
			
			while(it.hasNext()){
				SelectionKey key = (SelectionKey)it.next();
				
				it.remove();
				
				if(key.isConnectable()){
					SocketChannel channel = (SocketChannel)key.channel();
					if(channel.isConnectionPending()){
						channel.finishConnect();
					}
					
					channel.configureBlocking(false);
					channel.write(ByteBuffer.wrap(new String("hello i am client").getBytes()));
					
					channel.register(selector, SelectionKey.OP_READ);
				}else if(key.isReadable()){
					//可读消息
					SocketChannel channel = (SocketChannel)key.channel();
					
					ByteBuffer by = ByteBuffer.allocate(1024);
					
					int read = channel.read(by);
					
					if(read > 0){
						byte[] data = by.array();
						
						String msg = new String(data).trim();
						System.out.println("get msg:" + msg);
						
						ByteBuffer out = ByteBuffer.wrap("client is here ".getBytes());
						channel.write(out);
					}
					
					System.out.println("client close connection");
					
					key.cancel();
				}
			}
			
		}
		
	}
}
