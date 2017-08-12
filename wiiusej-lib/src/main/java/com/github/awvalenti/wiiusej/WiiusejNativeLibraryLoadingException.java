package com.github.awvalenti.wiiusej;

/**
 * @author awvalenti
 */
public class WiiusejNativeLibraryLoadingException extends Exception {

	private static final long serialVersionUID = 1L;

	public WiiusejNativeLibraryLoadingException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return ""
				+ "Unable to load native library. When trying to copy"
				+ " from JAR to temp dir and load:\n"
				+ getCause();
	}

}
