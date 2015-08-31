package wiiusej;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClasspathResourceExporter {

	public static String exportToTempFile(String resourcePath) {
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

		} catch (IOException e) {
			throw new RuntimeException(e);

		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (output != null) output.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
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
