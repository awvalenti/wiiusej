package com.github.awvalenti.wiiusej;

public class WiiusejNativeLibrariesLoadingException extends Exception {

	private static final long serialVersionUID = 1L;

	public WiiusejNativeLibrariesLoadingException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return ""
				+ "Unable to load native libraries. When trying to copy"
				+ " from JAR to temp dir and load:\n"
				+ getCause();
	}

}
