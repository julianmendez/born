package de.tudresden.inf.lat.problogapi;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;

import de.tudresden.inf.lat.born.core.term.Symbol;

/**
 * An object of this class manages the installation of ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProcessor implements QueryProcessor {

	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_INSTALL_COMMAND = "install";
	static final String PROBLOG_OUTPUT_OPTION = "-o";
	static final String PYTHON = "python";

	static final String FILE_SEPARATOR = Symbol.FILE_SEPARATOR;
	static final String PROBLOG_EXEC_WINDOWS = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "windows"
			+ FILE_SEPARATOR + "dsharp.exe";
	static final String PROBLOG_EXEC_DARWIN = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "darwin"
			+ FILE_SEPARATOR + "dsharp";
	static final String PROBLOG_EXEC_LINUX = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "linux"
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
		String info = "" + (current - start) + Symbol.TAB_AND_COLON + str;
		if (this.isShowingLog) {
			System.out.println(info);
		}
	}

	/**
	 * Decompresses the ProbLog ZIP file and returns the directory name.
	 * 
	 * @param start
	 *            execution start
	 * @param problogZipFile
	 *            file name of ProbLog ZIP file
	 * @param problogDirectory
	 *            directory where ProbLog is being installed
	 * @return the directory name
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
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_WINDOWS)).setExecutable(true);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_DARWIN)).setExecutable(true);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_LINUX)).setExecutable(true);
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
		String commandLine = PYTHON + Symbol.SPACE_CHAR + problogDirectory + FILE_SEPARATOR + PROBLOG_CLI
				+ Symbol.SPACE_CHAR + PROBLOG_INSTALL_COMMAND;
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

	/**
	 * Downloads and installs ProbLog.
	 * 
	 * @param start
	 *            execution start
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 */
	public void install(long start) throws IOException, InterruptedException {
		ProblogDownloadManager downloadManager = new ProblogDownloadManager();
		downloadManager.downloadIfNecessary();

		String directory = decompressProblog(start, downloadManager.getProblogZipFile(),
				ResourceConstant.DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
		this.problogDirectory = ResourceConstant.DEFAULT_PROBLOG_INSTALLATION_DIRECTORY + FILE_SEPARATOR + directory;
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
					} catch (IOException | InterruptedException e) {
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
				String commandLine = PYTHON + Symbol.SPACE_CHAR + this.problogDirectory + FILE_SEPARATOR + PROBLOG_CLI
						+ Symbol.SPACE_CHAR + (new File(ResourceConstant.PROBLOG_OUTPUT_FILE)).getAbsolutePath()
						+ Symbol.SPACE_CHAR + PROBLOG_OUTPUT_OPTION + Symbol.SPACE_CHAR
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
