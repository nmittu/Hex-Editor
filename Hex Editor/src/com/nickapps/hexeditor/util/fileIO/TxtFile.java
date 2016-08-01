package com.nickapps.hexeditor.util.fileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;

public class TxtFile extends File {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8322091929550143875L;

	public TxtFile(String pathname) {
		super(pathname);
	}

	public TxtFile(String filterPath, String fileName) {
		// TODO Auto-generated constructor stub
		super(filterPath, fileName);
	}

	public boolean write(String data) {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(this), "utf-8"))) {
			writer.write(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String read() throws IOException{
		String ret = new String(Files.readAllBytes(this.toPath()));
		return ret;
	}
	
	public boolean isSaved(String str) throws IOException{
		if(read().equals(str)){
			return true;
		}
		return false;
	}

}
