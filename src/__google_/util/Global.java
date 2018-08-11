package __google_.util;

import __google_.io.FileIO;

import java.io.File;

public class Global {
	private static final String cachePath = ".6oogle/";

	public static boolean debug = false;

	public static File getCache(String name){
		return new File(FileIO.prefix + cachePath + name);
	}
}
