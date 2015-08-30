package wiiusej;

class Platform {
	public final String operatingSystem;
	public final String architectureSuffix;
	public final String libPrefix;
	public final String libSuffix;

	private Platform(String operatingSystem, String architectureSuffix,
			String libPrefix, String libSuffix) {
		this.libPrefix = libPrefix;
		this.operatingSystem = operatingSystem;
		this.architectureSuffix = architectureSuffix;
		this.libSuffix = libSuffix;
	}

	public static Platform identify() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win")) {
			return new Platform("windows", identifyArchitecture(), "", ".dll");

		} else if (osName.contains("mac") || osName.contains("nix") || osName.contains("nux")
				|| osName.contains("aix")) {
			return new Platform("unix-like", identifyArchitecture(), "lib", ".so");

		}
		return new Platform("unknown", null, null, null);
	}

	private static String identifyArchitecture() {
		return System.getProperty("os.arch").contains("64") ? "x64" : "i386";
	}

	public boolean isUnknown() {
		return operatingSystem.equals("unknown");
	}

}