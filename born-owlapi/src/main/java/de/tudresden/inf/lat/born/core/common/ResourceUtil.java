package de.tudresden.inf.lat.born.core.common;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class contains convenience methods.
 * 
 * @author Julian Mendez
 *
 */
public class ResourceUtil {

	/**
	 * Creates a parent path for the given file name.
	 * 
	 * @param fileName
	 *            file name
	 * @return the given file name
	 * @throws IOException
	 *             if something went wrong with I/O
	 */
	public static String ensurePath(String fileName) throws IOException {
		ensurePath(new File(fileName));
		return fileName;
	}

	/**
	 * Creates a parent path for the given file.
	 * 
	 * @param file
	 *            file
	 * @return the given file
	 * @throws IOException
	 *             if something went wrong with I/O
	 */
	public static File ensurePath(File file) throws IOException {
		Objects.requireNonNull(file);
		String parent = file.getParent();
		if (Objects.nonNull(parent)) {
			(new File(parent)).mkdirs();
		}
		return file;
	}

}
