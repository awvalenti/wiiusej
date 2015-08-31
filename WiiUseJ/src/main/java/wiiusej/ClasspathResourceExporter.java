package wiiusej;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClasspathResourceExporter {

	public static String exportToTempFile(String resourcePath, String fileSuffix) {
		String filePath = System.getProperty("java.io.tmpdir");
		String fileName = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);
		File tempFile = new File(filePath + fileName);

		InputStream input = null;
		OutputStream output = null;

		try {
			input = ClasspathResourceExporter.class.getResourceAsStream(resourcePath);
			output = new FileOutputStream(tempFile);
			transferStreams(input, output);
			return tempFile.getAbsolutePath();

		} catch (IOException e) {
			throw new RuntimeException(e);

		} finally {
			try {
				if (input != null) input.close();
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
