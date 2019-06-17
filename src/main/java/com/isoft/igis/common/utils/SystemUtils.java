package com.isoft.igis.common.utils;

public class SystemUtils {

	public static String getOSName()
	{
		return System.getProperties().getProperty("os.name").toLowerCase();
	}

	public static boolean isWindows()
	{
		return getOSName().startsWith("windows");
	}

	public static boolean isMacOS()
	{
		return getOSName().startsWith("mac");
	}

	public static boolean isLinux()
	{
		return getOSName().startsWith("linux");
	}

}
