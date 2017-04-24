package com.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadDeadLock implements Runnable{
	int a,b;
	
	public ThreadDeadLock(int a,int b){
		this.a = a;
		this.b = b;
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		
		for(int i=0;i<100;i++){
			new Thread(new ThreadDeadLock(1,2)).start();
			new Thread(new ThreadDeadLock(2,1)).start();
		}
	}

	public void run() {
		synchronized (Integer.valueOf(a)){
			synchronized(Integer.valueOf(b)){
				System.out.println(a+b);
			}
		}
	}

	
}
