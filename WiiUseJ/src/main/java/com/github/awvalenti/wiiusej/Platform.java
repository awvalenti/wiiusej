package com.github.awvalenti.wiiusej;

/**
 * @author awvalenti
 */
public class Platform {
	public final String operatingSystem;
	public final String architecture;
	public final String libPrefix;
	public final String extension;

	private Platform(String operatingSystem, String architecture, String libPrefix, String extension) {
		this.libPrefix = libPrefix;
		this.operatingSystem = operatingSystem;
		this.architecture = architecture;
		this.extension = extension;
	}

	public static Platform identify() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win")) {
			return new Platform("windows", identifyArchitecture(), "", "dll");

		} else if (osName.contains("mac") || osName.contains("nix") || osName.contains("nux")
				|| osName.contains("aix")) {
			return new Platform("unix-like", identifyArchitecture(), "lib", "so");

		}
		throw new RuntimeException("Unable to identify operating system: "
				+ System.getProperty("os.name"));
	}

	private static String identifyArchitecture() {
		return System.getProperty("os.arch").contains("64") ? "x64" : "i386";
	}

}
