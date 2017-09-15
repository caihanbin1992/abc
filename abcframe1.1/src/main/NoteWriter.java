package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class NoteWriter {

	public static void write(String str, String filename, boolean append) throws Exception {
		BufferedWriter writer;
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append)));
		writer.write(str);
		writer.flush();
		writer.close();
	}
	
	public static void main(String[] args) {
		try {
			write("hi \r\n hi", "E:/desktop/a.txt", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
