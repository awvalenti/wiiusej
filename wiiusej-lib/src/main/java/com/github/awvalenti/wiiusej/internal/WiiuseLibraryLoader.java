package com.github.awvalenti.wiiusej.internal;

import com.github.awvalenti.wiiusej.WiiusejNativeLibraryLoadingException;

/**
 * @author awvalenti
 */
public class WiiuseLibraryLoader {

	private boolean successfullyLoaded = false;

	public synchronized void loadIfNotLoadedYet() throws WiiusejNativeLibraryLoadingException {
		if (!successfullyLoaded) {
			try {
				copyFromJarAndLoadFromTempDir();
				successfullyLoaded = true;
			} catch (Throwable t) {
				throw new WiiusejNativeLibraryLoadingException(t);
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
