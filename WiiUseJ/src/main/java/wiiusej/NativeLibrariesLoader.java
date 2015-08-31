package wiiusej;

import java.io.IOException;

/**
 * @author awvalenti
 */
public class NativeLibrariesLoader {

	public static void loadLibs() {
		try {
			firstAttempt();
		} catch (UnsatisfiedLinkError e1) {
			try {
				secondAttempt();
			} catch (Throwable e2) {
				System.err.println("Unable to load native libraries.");
				System.err.println("\nWhen trying to look at default lib paths:");
				e1.printStackTrace();
				System.err.println("\nWhen trying to copy from JAR to temp dir and load:");
				e2.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	private static void firstAttempt() {
		System.loadLibrary("wiiusej");
	}

	private static void secondAttempt() throws IOException {
		Platform p = Platform.identify();
		String wiiuseLibPath = ClasspathResourceExporter.exportToTempFile(String.format(
				"/wiiusej/nativelibs/%s/%s/%swiiuse.%s", p.operatingSystem, p.architectureSuffix,
				p.libPrefix, p.extension));
		String wiiusejLibPath = ClasspathResourceExporter.exportToTempFile(String.format(
				"/wiiusej/nativelibs/%s/%s/%swiiusej.%s", p.operatingSystem, p.architectureSuffix,
				p.libPrefix, p.extension));
		Runtime.getRuntime().load(wiiuseLibPath);
		Runtime.getRuntime().load(wiiusejLibPath);
	}

}
