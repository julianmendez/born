package de.tudresden.inf.lat.jproblog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * An object of this class decompresses a ZIP file.
 * 
 * @author Julian Mendez
 * 
 */
public class Decompressor {

	/**
	 * Creates parent directories for the given file name, if necessary.
	 * 
	 * @param fileName
	 *            file name
	 * @return the same given file name
	 */
	public static String ensurePath(String fileName) {
		ensurePath(new File(fileName));
		return fileName;
	}

	/**
	 * Creates parent directories for the given file, if necessary.
	 * 
	 * @param file
	 *            file
	 * @return the same given file
	 */
	public static File ensurePath(File file) {
		Objects.requireNonNull(file);
		String parent = file.getParent();
		if (Objects.nonNull(parent)) {
			(new File(parent)).mkdirs();
		}
		return file;
	}

	/**
	 * Stores a file.
	 * 
	 * @param inputStream
	 *            input stream
	 * @param outputFile
	 *            output file
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	void storeFile(InputStream inputStream, File outputFile) throws IOException {
		Objects.requireNonNull(inputStream);
		Objects.requireNonNull(outputFile);
		BufferedInputStream input = new BufferedInputStream(inputStream);
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(ensurePath(outputFile)));
		for (int ch = input.read(); ch != -1; ch = input.read()) {
			output.write(ch);
		}
		output.flush();
		output.close();
	}

	/**
	 * Decompresses a ZIP file and returns the first decompressed entry. If the
	 * ZIP file contains only one directory on the root, this returned is this
	 * directory.
	 * 
	 * @param compressedFile
	 *            compressed file
	 * @param outputDirectory
	 *            output directory
	 * @return the first decompressed entry
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public ZipEntry decompress(File compressedFile, File outputDirectory) throws IOException {
		Objects.requireNonNull(compressedFile);
		Objects.requireNonNull(outputDirectory);
		ZipEntry ret = null;
		if (!outputDirectory.exists()) {
			outputDirectory.mkdirs();
		}
		ZipInputStream input = new ZipInputStream(new FileInputStream(compressedFile));
		for (ZipEntry entry = input.getNextEntry(); Objects.nonNull(entry); entry = input.getNextEntry()) {
			if (Objects.isNull(ret)) {
				ret = entry;
			}

			String fileName = entry.getName();
			File newFile = new File(outputDirectory + File.separator + fileName);

			(new File(newFile.getParent())).mkdirs();

			if (entry.isDirectory()) {
				newFile.mkdirs();
			} else {
				storeFile(input, newFile);
			}

		}
		input.closeEntry();
		input.close();
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Decompressor)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

}
