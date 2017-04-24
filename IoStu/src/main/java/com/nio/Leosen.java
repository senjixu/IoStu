package com.nio;

import java.util.ArrayList;
import java.util.List;

public class Leosen {
	private static final String HELLO = "hello";
	
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(4000);
		fillHeap(5000);
		System.out.println("结束");
	}
	
	static class OOMobject{
		public byte[] placeholder = new byte[64 * 1024];
	}
	
	public static void fillHeap(int num) throws InterruptedException{
		List<OOMobject> list = new ArrayList<OOMobject>();
		
		for(int i=0;i<num;i++){
			Thread.sleep(50);
			list.add(new OOMobject());
		}
		System.gc();
		System.out.println("gc完成");
		Thread.sleep(4000);
	}
}
