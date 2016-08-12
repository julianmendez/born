package de.tudresden.inf.lat.jproblog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * An object of this class manages the download process of a given file.
 * 
 * @author Julian Mendez
 *
 */
public class DownloadManager {

	final String MD5 = "MD5";
	final String SHA_1 = "SHA-1";
	final String VERIFICATION_FILE_EXTENSION = ".sha1";

	private final URI downloadUri;
	private final String fileName;
	private final String verificationFile;

	/**
	 * Creates a new download manager.
	 * 
	 * @param downloadUri
	 *            download URI
	 * @param fileName
	 *            file name
	 */
	public DownloadManager(URI downloadUri, String fileName) {
		Objects.nonNull(downloadUri);
		Objects.nonNull(fileName);
		this.downloadUri = downloadUri;
		this.fileName = fileName;
		this.verificationFile = fileName + VERIFICATION_FILE_EXTENSION;
	}

	/**
	 * Returns the name of the file to be downloaded.
	 * 
	 * @return the name of the file to be downloaded
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Returns the URI used to download the file.
	 * 
	 * @return the URI used to download the file
	 */
	public URI getDownloadUri() {
		return this.downloadUri;
	}

	/**
	 * Checks if it is necessary to download the file, and in that case, it
	 * downloads the file.
	 * 
	 * @throws IOException
	 *             if something went wrong with the input/output
	 */
	public void downloadIfNecessary() throws IOException {
		boolean downloadIsNecessary = checkIfDownloadIsNecessary(fileName, verificationFile);
		if (downloadIsNecessary) {
			download(downloadUri, fileName);
			String verificationCode = computeVerificationCode(fileName);
			storeVerificationCode(verificationCode, verificationFile);
		}
	}

	/**
	 * Downloads the file from the given URI.
	 * 
	 * @param downloadUri
	 *            URI to download the file
	 * @param fileName
	 *            file name
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	void download(URI downloadUri, String fileName) throws IOException {
		ReadableByteChannel channel = Channels.newChannel(downloadUri.toURL().openStream());
		FileOutputStream output = new FileOutputStream(Decompressor.ensurePath(fileName));
		output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
		output.close();
	}

	String readVerificationCode(String verificationFile) {
		String ret = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(verificationFile));
			StringBuilder sb = new StringBuilder();
			reader.lines().forEach(line -> {
				sb.append(line.trim());
			});
			reader.close();
			ret = sb.toString();
		} catch (IOException e) {
		}
		return ret;
	}

	void storeVerificationCode(String code, String file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(Decompressor.ensurePath(file)));
		writer.write(code.trim());
		writer.flush();
		writer.close();
	}

	byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BufferedInputStream input = new BufferedInputStream(inputStream);
		for (int ch = input.read(); ch != -1; ch = input.read()) {
			output.write(ch);
		}
		return output.toByteArray();
	}

	String computeVerificationCode(String fileName) {
		String ret = "";
		try {
			ret = asHexString(computeVerificationCode(getBytes(new FileInputStream(fileName))));
		} catch (IOException e) {

		}
		return ret;
	}

	byte[] computeVerificationCode(byte[] content) throws IOException {
		MessageDigest md;
		byte[] digest;
		try {
			md = MessageDigest.getInstance(SHA_1);
			md.update(content);
			digest = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
		return digest;
	}

	String asHexString(byte[] buffer) {
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, buffer.length).forEach(index -> {
			int value = Byte.toUnsignedInt(buffer[index]);
			String valueStr = ((value < 0x10) ? "0" : "") + Integer.toHexString(value);
			sb.append("" + valueStr);
		});
		return sb.toString();
	}

	boolean checkIfDownloadIsNecessary(String fileName, String verificationFile) {
		String storedVerificationCode = readVerificationCode(verificationFile);
		String verificationCode = computeVerificationCode(fileName);
		return storedVerificationCode.isEmpty()
				|| verificationCode.isEmpty() && !verificationCode.equals(storedVerificationCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof DownloadManager)) {
			return false;
		} else {
			DownloadManager other = (DownloadManager) obj;
			return this.getDownloadUri().equals(other.getDownloadUri())
					&& this.getFileName().equals(other.getFileName());
		}
	}

	@Override
	public int hashCode() {
		return this.downloadUri.hashCode() + 0x1F * +this.fileName.hashCode();
	}

	@Override
	public String toString() {
		return this.downloadUri.toASCIIString() + " -> " + this.fileName;
	}

}
