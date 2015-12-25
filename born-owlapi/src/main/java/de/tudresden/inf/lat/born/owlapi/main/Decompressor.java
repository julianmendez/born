package de.tudresden.inf.lat.born.owlapi.main;

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
		BufferedInputStream input = new BufferedInputStream(inputStream);
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));
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
		for (ZipEntry entry = input.getNextEntry(); !Objects.isNull(entry); entry = input.getNextEntry()) {
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

}
