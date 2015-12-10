package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;

import de.tudresden.inf.lat.born.owlapi.main.Decompressor;

/**
 * An object of this class manages the installation of ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProcessor implements QueryProcessor {

	static final String SLASH = "/";
	static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI.create("https://bitbucket.org/problog/problog/get/master.zip");
	static final String DEFAULT_PROBLOG_ZIP_FILE = "problog-2.1-SNAPSHOT.zip";
	static final String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = "/tmp";
	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_INSTALL_COMMAND = "install";
	static final String PROBLOG_OUTPUT_FILE = "/tmp/~tmp-output.pl.tmp";
	static final String PROBLOG_OUTPUT_OPTION = "-o";
	static final String PYTHON = "python";
	static final String SPACE = " ";
	static final String LONG_TAB = "\t    : ";

	static final String PROBLOG_EXEC_LINUX = "problog/bin/windows/dsharp.exe";
	static final String PROBLOG_EXEC_DARWIN = "problog/bin/darwin/dsharp";
	static final String PROBLOG_EXEC_WINDOWS = "problog/bin/linux/dsharp";

	private boolean isShowingLog = false;
	private String problogDirectory = null;
	private Object problogInstallationMonitor = new Object();

	public ProblogProcessor() {
		this.problogDirectory = null;
	}

	public ProblogProcessor(String problogDirectory) {
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
		log("Download ProbLog.", start);
		ReadableByteChannel channel = Channels.newChannel(DEFAULT_PROBLOG_DOWNLOAD_URI.toURL().openStream());
		FileOutputStream output = new FileOutputStream(problogZipFile);
		output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
		output.close();
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
		log("Update permissions.", start);
		(new File(problogDirectory + SLASH + PROBLOG_EXEC_LINUX)).setExecutable(true);
		(new File(problogDirectory + SLASH + PROBLOG_EXEC_DARWIN)).setExecutable(true);
		(new File(problogDirectory + SLASH + PROBLOG_EXEC_WINDOWS)).setExecutable(true);
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
		log("Install ProbLog.", start);
		String commandLine = PYTHON + SPACE + problogDirectory + SLASH + PROBLOG_CLI + SPACE + PROBLOG_INSTALL_COMMAND;
		log(commandLine, start);
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(commandLine);
		return process.waitFor();
	}

	public boolean isProblogNeeded() {
		return this.problogDirectory == null;
	}

	public String getProblogDirectory() {
		return this.problogDirectory;
	}

	public void install(long start) throws IOException, URISyntaxException, InterruptedException {
		downloadProblog(start, DEFAULT_PROBLOG_ZIP_FILE);
		String directory = decompressProblog(start, DEFAULT_PROBLOG_ZIP_FILE, DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
		this.problogDirectory = DEFAULT_PROBLOG_INSTALLATION_DIRECTORY + SLASH + directory;
		updatePermissions(start, problogDirectory);
		installProblog(start, problogDirectory);
	}

	/**
	 * Starts the installation of ProbLog.
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
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public int execute(long start, String outputFileName) {
		synchronized (this.problogInstallationMonitor) {

		}

		try {
			log("Execute ProbLog.", start);
			Runtime runtime = Runtime.getRuntime();
			String commandLine = PYTHON + SPACE + this.problogDirectory + SLASH + PROBLOG_CLI + SPACE
					+ (new File(PROBLOG_OUTPUT_FILE)).getAbsolutePath() + SPACE + PROBLOG_OUTPUT_OPTION + SPACE
					+ (new File(outputFileName)).getAbsolutePath();
			log(commandLine, start);
			Process process = runtime.exec(commandLine);
			return process.waitFor();
		} catch (InterruptedException | IOException e) {
			throw new RuntimeException(e);
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
