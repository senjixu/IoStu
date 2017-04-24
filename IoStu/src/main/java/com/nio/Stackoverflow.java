package com.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Stackoverflow {
	private static int a = 0;
	public static void main(String[] args) throws Throwable{
		try{
			stackLeak();
		}catch(Throwable e){
			System.out.println(a);
			throw e;
		}
		
	}

	public static void stackLeak(){
		
		a++;
		stackLeak();
	}
	
}
