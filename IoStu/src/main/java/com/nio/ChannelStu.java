package com.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class ChannelStu {


	/**
	 * 使用Buffer读写数据一般遵循以下四个步骤：<br/>
		1.写入数据到Buffer <br/>
		2.调用flip()方法  <br/>
		3.从Buffer中读取数据  <br/>
		4.调用clear()方法或者compact()方法 <br/>
	 */
	public static void main(String[] args) throws Exception{
		RandomAccessFile afile = null;
		try{
			afile = new RandomAccessFile("d:/java face.txt","rw");
			FileChannel channel = afile.getChannel();

			ByteBuffer buffer = ByteBuffer.allocate(48);
			

			int inByteRead = channel.read(buffer);

			List<Byte> dst = new ArrayList<Byte>();
			while(inByteRead != -1){
				//System.out.println("read  " + inByteRead);

				//从写模式切换到读模式
				buffer.flip();
				
				while(buffer.hasRemaining()){
					dst.add(buffer.get());
				}
				//清除整个缓冲区
				buffer.clear();
				//清除读过的部分
				//buffer.compact();
				inByteRead = channel.read(buffer);
			}
			
			byte[] abc = new byte[dst.size()];
			
			for(int i = 0;i<dst.size();i++){
				abc[i] = dst.get(i);
			}
			
			System.out.println(new String(abc,"GBK"));
			
		}finally{
			if(afile != null)afile.close();
		}
		
	}
}