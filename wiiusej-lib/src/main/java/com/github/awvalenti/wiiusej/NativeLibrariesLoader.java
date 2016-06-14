package com.github.awvalenti.wiiusej;

/**
 * @author awvalenti
 */
public class NativeLibrariesLoader {

	private boolean successfullyLoaded = false;

	public synchronized void loadLibsIfNotLoaded() throws WiiusejNativeLibrariesLoadingException {
		if (!successfullyLoaded) {
			try {
				copyFromJarAndLoadFromTempDir();
				successfullyLoaded = true;
			} catch (Throwable t) {
				throw new WiiusejNativeLibrariesLoadingException(t);
			}
		}
	}

	private void copyFromJarAndLoadFromTempDir() throws Throwable {
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
