package com.nickapps.hexeditor.util.fileIO;

import java.io.File;
import java.io.IOException;
import java.security.SecureClassLoader;

import org.apache.commons.io.FileUtils;

public class FileClassLoader extends SecureClassLoader {
	public Object createObjectFromFile(String fileName)
			throws InstantiationException, IOException, IllegalAccessException {

		File file = new File(fileName);
		byte[] classBytes = FileUtils
				.readFileToByteArray(file);
		Class<?> clazz = defineClass(null, classBytes, 0, classBytes.length);
		return clazz.newInstance();
	}
	
}
