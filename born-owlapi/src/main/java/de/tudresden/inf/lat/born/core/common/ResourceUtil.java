package de.tudresden.inf.lat.born.core.common;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ResourceUtil {

	public static String ensurePath(String fileName) throws IOException {
		ensurePath(new File(fileName));
		return fileName;
	}

	public static File ensurePath(File file) throws IOException {
		Objects.requireNonNull(file);
		String parent = file.getParent();
		if (Objects.nonNull(parent)) {
			(new File(parent)).mkdirs();
		}
		return file;
	}

}
