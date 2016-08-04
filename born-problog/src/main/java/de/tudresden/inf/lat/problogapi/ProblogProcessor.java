package de.tudresden.inf.lat.problogapi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;

/**
 * An object of this class manages the installation of ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProcessor implements Function<String, String> {

	public static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI
			.create("https://bitbucket.org/problog/problog/get/master.zip");

	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	public static final String USER_HOME_DIRECTORY = System.getProperty("user.home");

	public static final String BORN_WORKING_DIRECTORY = USER_HOME_DIRECTORY + FILE_SEPARATOR + ".cache" + FILE_SEPARATOR
			+ "born";

	public static final String DEFAULT_INPUT_FILE_FOR_PROBLOG = BORN_WORKING_DIRECTORY + FILE_SEPARATOR
			+ "input_for_problog.txt";

	public static final String DEFAULT_OUTPUT_FILE_FROM_PROBLOG = BORN_WORKING_DIRECTORY + FILE_SEPARATOR
			+ "output_from_problog.txt";

	public static final String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = BORN_WORKING_DIRECTORY;

	public static final String DEFAULT_PROBLOG_ZIP_FILE = BORN_WORKING_DIRECTORY + FILE_SEPARATOR
			+ "problog-2.1-SNAPSHOT.zip";

	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_INSTALL_COMMAND = "install";
	static final String PROBLOG_OUTPUT_OPTION = "-o";
	static final String PYTHON_COMMAND = "python";

	static final String PROBLOG_EXEC_WINDOWS = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "windows"
			+ FILE_SEPARATOR + "dsharp.exe";
	static final String PROBLOG_EXEC_DARWIN = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "darwin"
			+ FILE_SEPARATOR + "dsharp";
	static final String PROBLOG_EXEC_LINUX = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "linux"
			+ FILE_SEPARATOR + "dsharp";

	static final char SPACE_CHAR = ' ';
	static final char NEW_LINE_CHAR = '\n';
	static final String TAB_AND_COLON = "\t    : ";

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
		String info = "" + (current - start) + TAB_AND_COLON + str;
		if (this.isShowingLog) {
			System.out.println(info);
		}
	}

	String flattenArguments(String[] args) {
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, args.length).forEach(index -> {
			sb.append(SPACE_CHAR);
			sb.append(args[index]);
		});
		return sb.toString();
	}

	int runPython(String[] args) {
		try {
			String commandLine = PYTHON_COMMAND + flattenArguments(args);
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commandLine);
			return process.waitFor();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
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
		log("Install ProbLog.", start);
		String[] args = new String[2];
		args[0] = problogDirectory + FILE_SEPARATOR + PROBLOG_CLI;
		args[1] = PROBLOG_INSTALL_COMMAND;
		return runPython(args);
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
		DownloadManager downloadManager = new DownloadManager(DEFAULT_PROBLOG_DOWNLOAD_URI, DEFAULT_PROBLOG_ZIP_FILE);
		downloadManager.downloadIfNecessary();

		String directory = decompressProblog(start, downloadManager.getFileName(),
				DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
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
			log("Execute ProbLog.", start);
			String[] args = new String[4];
			args[0] = this.problogDirectory + FILE_SEPARATOR + PROBLOG_CLI;
			args[1] = (new File(DEFAULT_INPUT_FILE_FOR_PROBLOG)).getAbsolutePath();
			args[2] = PROBLOG_OUTPUT_OPTION;
			args[3] = (new File(outputFileName)).getAbsolutePath();
			return runPython(args);
		}
	}

	/**
	 * Returns string with the content of a given reader.
	 * 
	 * @param input
	 *            reader
	 * @return string with the content of a given reader
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	String show(Reader input) throws IOException {
		StringBuilder sb = new StringBuilder();
		Objects.requireNonNull(input);
		BufferedReader reader = new BufferedReader(input);
		for (String line = reader.readLine(); Objects.nonNull(line); line = reader.readLine()) {
			sb.append(line);
			sb.append(NEW_LINE_CHAR);
		}
		return sb.toString();
	}

	void createInputFileForProblog(String input, String inputFileForProblog) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileForProblog));
		writer.write(input);
		writer.flush();
		writer.close();
	}

	@Override
	public String apply(String input) {
		try {
			createInputFileForProblog(input, DEFAULT_INPUT_FILE_FOR_PROBLOG);
			execute(0, DEFAULT_OUTPUT_FILE_FROM_PROBLOG);
			File outputFile = new File(DEFAULT_OUTPUT_FILE_FROM_PROBLOG);
			if (outputFile.exists()) {
				return show(new InputStreamReader(new FileInputStream(outputFile)));
			} else {
				return "No results.";
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
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
