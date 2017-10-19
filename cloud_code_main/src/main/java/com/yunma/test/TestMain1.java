package com.yunma.test;

import java.io.File;

import org.junit.Test;

public class TestMain1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		 		File f = new File("TileTest.java");
//		      String fileName=f.getName();
//		      String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
//		      System.out.println(prefix);
		

	}
	@Test
	public void test1(){
//		 		File f = new File("TileTest.java");
//		      String fileName=f.getName();
//		      String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
//		      System.out.println(prefix);
		File f = new File("test.java");
		System.out.println( f.getName().substring(f.getName().lastIndexOf(".")+1));

		
	}

}
