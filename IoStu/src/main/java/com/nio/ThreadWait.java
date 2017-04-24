package com.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadWait {
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		createBusyThread();
		Object obj = new Object();
		createLockThread(obj);
	}

	public static void createBusyThread(){
		Thread thread = new Thread(new Runnable(){

			public void run() {
				while(true){
					;
				}
			}
			
		},"testBysyThread");
		thread.start();
	}
	
	public static void createLockThread(final Object lock){
		Thread thread = new Thread(new Runnable(){
			public void run() {
				synchronized(lock){
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		},"threadLockThread");
		thread.start();
	}
}
