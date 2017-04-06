package com.example.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoTest {
	
	public void testInput() throws IOException{
		File f = new File("d:" + File.separator+"test.txt");
		InputStream in = new FileInputStream(f);
		byte[] b=new byte[(int) f.length()];
		in.read(b);//读入到该参数里面
		in.close();
		System.out.println(new String(b));
	}
	
	public void testOutput() throws Exception{
		File file = new File("d:"+File.separator+"test.txt");
		OutputStream outputStream = new FileOutputStream(file,true);
		String woString ="hello word";
		char[] tep = woString.toCharArray();
		for(char c:tep){
			outputStream.write(c);
		}
		outputStream.close();
		
	}

	public  static void main(String args[]) throws Exception{
		IoTest test = new IoTest();
		test.testOutput();
		test.testInput();
	}
	
}
