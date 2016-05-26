package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;

import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.main.Decompressor;

/**
 * An object of this class manages the installation of ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProcessor implements QueryProcessor {

	final String MD5 = "MD5";
	final String SHA_1 = "SHA-1";
	final String SHA_256 = "SHA-256";

	static final String FILE_SEPARATOR = Symbol.FILE_SEPARATOR;
	static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI.create("https://bitbucket.org/problog/problog/get/master.zip");
	static final String DEFAULT_PROBLOG_ZIP_FILE = "problog-2.1-SNAPSHOT.zip";
	static final String DEFAULT_VERIFICATION_FILE = DEFAULT_PROBLOG_ZIP_FILE + ".sha";
	static final String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = FILE_SEPARATOR + "tmp";
	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_INSTALL_COMMAND = "install";
	static final String PROBLOG_OUTPUT_FILE = FILE_SEPARATOR + "tmp" + FILE_SEPARATOR + "~tmp-output.pl.tmp";
	static final String PROBLOG_OUTPUT_OPTION = "-o";
	static final String PYTHON = "python";
	static final String SPACE = " ";
	static final String LONG_TAB = "\t    : ";

	static final String PROBLOG_EXEC_LINUX = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "windows"
			+ FILE_SEPARATOR + "dsharp.exe";
	static final String PROBLOG_EXEC_DARWIN = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "darwin"
			+ FILE_SEPARATOR + "dsharp";
	static final String PROBLOG_EXEC_WINDOWS = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "linux"
			+ FILE_SEPARATOR + "dsharp";

	private boolean isShowingLog = false;
	private String problogDirectory = null;
	private Object problogInstallationMonitor = new Object();

	public ProblogProcessor() {
		this.problogDirectory = null;
	}

	public ProblogProcessor(String problogDirectory) {
		Objects.requireNonNull(problogDirectory);
		this.problogDirectory = problogDirectory;
	}

	/**
	 * Shows the entry on the standard output, if logging is enabled.
	 * 
	 * @param str
	 *            entry to log
	 * @param start
	 *            execution start
	 */
	void log(String str, long start) {
		Objects.requireNonNull(str);
		long current = System.nanoTime();
		String info = "" + (current - start) + LONG_TAB + str;
		if (this.isShowingLog) {
			System.out.println(info);
		}
	}

	/**
	 * Downloads ProbLog from the default download URI.
	 * 
	 * @param start
	 *            execution start
	 * @param problogZipFile
	 *            file name of the ProbLog ZIP file
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws URISyntaxException
	 *             if the default URI is not valid
	 */
	void downloadProblog(long start, String problogZipFile) throws IOException, URISyntaxException {
		Objects.requireNonNull(problogZipFile);
		log("Download ProbLog.", start);
		ReadableByteChannel channel = Channels.newChannel(DEFAULT_PROBLOG_DOWNLOAD_URI.toURL().openStream());
		FileOutputStream output = new FileOutputStream(problogZipFile);
		output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
		output.close();
	}

	String readVerificationCode() {
		String ret = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(DEFAULT_VERIFICATION_FILE));
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

	void storeVerificationCode(String code) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(DEFAULT_VERIFICATION_FILE));
		writer.write(code.trim());
		writer.newLine();
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

	String computeVerificationCode() {
		String ret = "";
		try {
			ret = asHexString(computeVerificationCode(getBytes(new FileInputStream(DEFAULT_PROBLOG_ZIP_FILE))));
		} catch (IOException e) {

		}
		return ret;
	}

	byte[] computeVerificationCode(byte[] content) throws IOException {
		Objects.requireNonNull(content);
		MessageDigest md;
		byte[] digest;
		try {
			md = MessageDigest.getInstance(SHA_256);
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

	/**
	 * Decompresses the ProbLog ZIP file.
	 * 
	 * @param start
	 *            execution start
	 * @param problogZipFile
	 *            file name of ProbLog ZIP file
	 * @param problogDirectory
	 *            directory where ProbLog is being installed
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	String decompressProblog(long start, String problogZipFile, String problogDirectory) throws IOException {
		Objects.requireNonNull(problogZipFile);
		Objects.requireNonNull(problogDirectory);
		log("Decompress ProbLog.", start);
		Decompressor installer = new Decompressor();
		ZipEntry directory = installer.decompress(new File(problogZipFile), new File(problogDirectory));
		return directory.getName();
	}

	/**
	 * Updates execute permission of key executable files.
	 * 
	 * @param start
	 *            execution start
	 * @param problogDirectory
	 *            directory where ProbLog is being installed
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	void updatePermissions(long start, String problogDirectory) throws IOException {
		Objects.requireNonNull(problogDirectory);
		log("Update permissions.", start);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_LINUX)).setExecutable(true);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_DARWIN)).setExecutable(true);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_WINDOWS)).setExecutable(true);
	}

	/**
	 * Installs ProbLog. This is necessary because just decompressing the ZIP
	 * file is not enough.
	 * 
	 * @param start
	 *            execution start
	 * @param problogDirectory
	 *            directory where ProbLog has been installed
	 * @return the exit value given by the operating system
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 */
	int installProblog(long start, String problogDirectory) throws IOException, InterruptedException {
		Objects.requireNonNull(problogDirectory);
		log("Install ProbLog.", start);
		String commandLine = PYTHON + SPACE + problogDirectory + FILE_SEPARATOR + PROBLOG_CLI + SPACE
				+ PROBLOG_INSTALL_COMMAND;
		log(commandLine, start);
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(commandLine);
		return process.waitFor();
	}

	public boolean isProblogNeeded() {
		return Objects.isNull(this.problogDirectory);
	}

	public String getProblogDirectory() {
		return this.problogDirectory;
	}

	boolean checkIfDownloadIsNecessary(String problogZipFile) {
		Objects.requireNonNull(problogZipFile);
		String storedVerificationCode = readVerificationCode();
		String verificationCode = computeVerificationCode();
		return storedVerificationCode.isEmpty()
				|| verificationCode.isEmpty() && !verificationCode.equals(storedVerificationCode);
	}

	/**
	 * Downloads and installs ProbLog.
	 * 
	 * @param start
	 *            execution start
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws URISyntaxException
	 *             if some URI is wrong
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 */
	public void install(long start) throws IOException, URISyntaxException, InterruptedException {
		boolean downloadIsNecessary = checkIfDownloadIsNecessary(DEFAULT_PROBLOG_ZIP_FILE);
		if (downloadIsNecessary) {
			downloadProblog(start, DEFAULT_PROBLOG_ZIP_FILE);
			String verificationCode = computeVerificationCode();
			storeVerificationCode(verificationCode);
		}

		String directory = decompressProblog(start, DEFAULT_PROBLOG_ZIP_FILE, DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
		this.problogDirectory = DEFAULT_PROBLOG_INSTALLATION_DIRECTORY + FILE_SEPARATOR + directory;
		updatePermissions(start, problogDirectory);
		installProblog(start, problogDirectory);
	}

	/**
	 * Starts the installation of ProbLog.
	 * 
	 * @param start
	 *            execution start
	 */
	public void startInstallation(long start) {
		Thread thread = new Thread() {
			public void run() {
				synchronized (problogInstallationMonitor) {
					try {
						install(start);
					} catch (IOException | URISyntaxException | InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		};
		thread.start();
	}

	/**
	 * Executes ProbLog and returns the exit value given by the operating
	 * system.
	 * 
	 * @param start
	 *            execution start
	 * @param outputFileName
	 *            file name of output
	 * @return the exit value given by the operating system
	 */
	public int execute(long start, String outputFileName) {
		Objects.requireNonNull(outputFileName);
		synchronized (this.problogInstallationMonitor) {

			try {
				log("Execute ProbLog.", start);
				Runtime runtime = Runtime.getRuntime();
				String commandLine = PYTHON + SPACE + this.problogDirectory + FILE_SEPARATOR + PROBLOG_CLI + SPACE
						+ (new File(PROBLOG_OUTPUT_FILE)).getAbsolutePath() + SPACE + PROBLOG_OUTPUT_OPTION + SPACE
						+ (new File(outputFileName)).getAbsolutePath();
				log(commandLine, start);
				Process process = runtime.exec(commandLine);
				return process.waitFor();
			} catch (InterruptedException | IOException e) {
				throw new RuntimeException(e);
			}

		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof ProblogProcessor)) {
			return false;
		} else {
			ProblogProcessor other = (ProblogProcessor) obj;
			return this.getProblogDirectory().equals(other.getProblogDirectory());
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.problogDirectory;
	}

}
