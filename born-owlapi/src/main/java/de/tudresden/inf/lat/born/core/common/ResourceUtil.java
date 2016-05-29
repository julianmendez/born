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

	public static void ensurePath(String fileName) throws IOException {
		Objects.requireNonNull(fileName);
		File file = new File(fileName);
		String parent = file.getParent();
		if (Objects.nonNull(parent)) {
			(new File(parent)).mkdirs();
		}
	}

}
