package com.github.awvalenti.wiiusej;

import java.io.IOException;

/**
 * @author awvalenti
 */
public class NativeLibrariesLoader {

	public static void loadLibs() {
		try {
			copyFromJarAndLoadFromTempDir();
		} catch (Throwable e2) {
			System.err.println(""
					+ "Unable to load native libraries. When trying to copy"
					+ " from JAR to temp dir and load:\n"
					+ "\t" + e2
					+ "");
			throw new RuntimeException();
		}
	}

	private static void copyFromJarAndLoadFromTempDir() throws IOException {
		Platform p = Platform.identify();

		String wiiuseLibPath = ClasspathResourceExporter.exportToTempFile(String.format(
				"/com/github/awvalenti/wiiusej/nativelibs/%s/%s/%swiiuse.%s", p.operatingSystem,
				p.architecture, p.libPrefix, p.extension));

		String wiiusejLibPath = ClasspathResourceExporter.exportToTempFile(String.format(
				"/com/github/awvalenti/wiiusej/nativelibs/%s/%s/%swiiusej.%s", p.operatingSystem,
				p.architecture, p.libPrefix, p.extension));

		Runtime.getRuntime().load(wiiuseLibPath);
		Runtime.getRuntime().load(wiiusejLibPath);
	}

}
