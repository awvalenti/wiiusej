package com.github.awvalenti.wiiusej;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author awvalenti
 */
public class ClasspathResourceExporter {

	/**
	 * Copies a classpath resource to a temp file
	 *
	 * @param resourcePath
	 *            path to the resource, like "/sounds/my-sound.mp3"
	 * @return absolute path of the temp file created
	 * @throws IOException In case any i/o operation fails
	 */
	public static String exportToTempFile(String resourcePath) throws IOException {
		InputStream input = ClasspathResourceExporter.class.getResourceAsStream(resourcePath);
		if (input == null) throw new NullPointerException("Resource not found on classpath: "
				+ resourcePath);

		String filePath = System.getProperty("java.io.tmpdir");
		String fileName = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
		File tempFile = new File(filePath, fileName);

		OutputStream output = null;
		try {
			output = new FileOutputStream(tempFile);
			transferStreams(input, output);
			return tempFile.getAbsolutePath();

		} finally {
			try {
				input.close();
			} finally {
				if (output != null) output.close();
			}
		}
	}

	private static void transferStreams(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[4096];
		while (input.available() > 0) {
			int size = input.read(buffer);
			output.write(buffer, 0, size);
		}
	}

}
