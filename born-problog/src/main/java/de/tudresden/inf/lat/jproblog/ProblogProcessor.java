package de.tudresden.inf.lat.jproblog;

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
 * An object of this class manages the installation and execution of ProbLog.
 * The processor detects whether a valid version of Python is already installed.
 * If Python is installed, the processor is in 'Python mode' and it uses Python
 * to run ProbLog. Otherwise, it downloads Jython from the Central Repository,
 * and uses Jython to run ProbLog.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProcessor implements Function<String, String> {

	public static final String JYTHON_GROUP_ID = "org.python";
	public static final String JYTHON_ARTIFACT_ID = "jython-standalone";
	public static final String JYTHON_VERSION = "2.7.0";

	public static final String MAVEN_REPOSITORY = "http://repo1.maven.org/maven2";

	static final char SPACE_CHAR = ' ';
	static final char NEW_LINE_CHAR = '\n';
	static final String TAB_AND_COLON = "\t    : ";
	static final char HYPHEN_CHAR = '-';
	static final char SLASH_CHAR = '/';

	public static final String JYTHON_JAR = "jython-standalone" + HYPHEN_CHAR + JYTHON_VERSION + ".jar";

	public static final URI DEFAULT_JYTHON_DOWNLOAD_URI = URI
			.create(MAVEN_REPOSITORY + SLASH_CHAR + JYTHON_GROUP_ID.replace('.', SLASH_CHAR) + SLASH_CHAR
					+ JYTHON_ARTIFACT_ID + SLASH_CHAR + JYTHON_VERSION + SLASH_CHAR + JYTHON_JAR);

	public static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI
			.create("https://bitbucket.org/problog/problog/get/master.zip");

	static final String FILE_SEPARATOR = System.getProperty("file.separator");

	static final String USER_HOME_DIRECTORY = System.getProperty("user.home");

	static final String JPROBLOG_WORKING_DIRECTORY = USER_HOME_DIRECTORY + FILE_SEPARATOR + ".cache" + FILE_SEPARATOR
			+ "jproblog";

	static final String DEFAULT_INPUT_FILE_FOR_PROBLOG = JPROBLOG_WORKING_DIRECTORY + FILE_SEPARATOR
			+ "input_for_problog.txt";

	static final String DEFAULT_OUTPUT_FILE_FROM_PROBLOG = JPROBLOG_WORKING_DIRECTORY + FILE_SEPARATOR
			+ "output_from_problog.txt";

	static final String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = JPROBLOG_WORKING_DIRECTORY;

	static final String DEFAULT_PROBLOG_ZIP_FILE = JPROBLOG_WORKING_DIRECTORY + FILE_SEPARATOR
			+ "problog-2.1-SNAPSHOT.zip";

	static final String DEFAULT_JYTHON_FILE = JPROBLOG_WORKING_DIRECTORY + FILE_SEPARATOR + JYTHON_JAR;

	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_INSTALL_COMMAND = "install";
	static final String PROBLOG_OUTPUT_OPTION = "-o";

	static final String PYTHON_COMMAND = "python";
	static final String PYTHON_VERSION_OPTION = "-V";
	static final String PYTHON_VERSION_2_7 = "Python 2.7";
	static final String PYTHON_VERSION_3_2 = "Python 3.2";
	static final String JAVA_COMMAND = "java";
	static final String JAVA_JAR_OPTION = "-jar";
	static final String JYTHON_COMMAND = JAVA_COMMAND + SPACE_CHAR + JAVA_JAR_OPTION + SPACE_CHAR + DEFAULT_JYTHON_FILE;

	static final String PROBLOG_EXEC_WINDOWS = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "windows"
			+ FILE_SEPARATOR + "dsharp.exe";
	static final String PROBLOG_EXEC_DARWIN = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "darwin"
			+ FILE_SEPARATOR + "dsharp";
	static final String PROBLOG_EXEC_LINUX = "problog" + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "linux"
			+ FILE_SEPARATOR + "dsharp";

	public static final String NO_RESULTS = "No results.";

	public static final int KNOWN_ISSUE_MAX_RETRIES = 5;
	public static final String KNOWN_ISSUE_MESSAGE = "DSharpError: DSharp has encountered an error. This is a known issue. See KNOWN_ISSUES for details on how to resolve this problem.";

	private String problogDirectory = null;
	private Object problogInstallationMonitor = new Object();
	private final boolean pythonMode;

	/**
	 * Constructs a new ProbLog processor
	 */
	public ProblogProcessor() {
		this.pythonMode = isPythonInstalled();
	}

	ProblogProcessor(boolean pythonMode) {
		this.pythonMode = pythonMode;
	}

	boolean isPythonInstalled() {
		try {
			ProcessBuilder builder = new ProcessBuilder(PYTHON_COMMAND, PYTHON_VERSION_OPTION);
			Process process = builder.start();
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = reader.readLine();
			return (line != null && (line.startsWith(PYTHON_VERSION_2_7) || line.startsWith(PYTHON_VERSION_3_2)));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Tells whether this processor is in 'Python mode', i.e. it uses Python
	 * instead of Jython.
	 * 
	 * @return <code>true</code> if this processor is in Python mode
	 */
	public boolean getPythonMode() {
		return this.pythonMode;
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

	int runJython(String[] args) {
		try {
			String commandLine = JYTHON_COMMAND + flattenArguments(args);
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commandLine);
			return process.waitFor();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	int run(String args[]) {
		if (this.pythonMode) {
			return runPython(args);
		} else {
			return runJython(args);
		}
	}

	/**
	 * Decompresses the ProbLog ZIP file and returns the directory name.
	 * 
	 * @param problogZipFile
	 *            file name of ProbLog ZIP file
	 * @param problogDirectory
	 *            directory where ProbLog is being installed
	 * @return the directory name
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	String decompressProblog(String problogZipFile, String problogDirectory) throws IOException {
		Objects.requireNonNull(problogZipFile);
		Objects.requireNonNull(problogDirectory);
		Decompressor installer = new Decompressor();
		ZipEntry directory = installer.decompress(new File(problogZipFile), new File(problogDirectory));
		return directory.getName();
	}

	/**
	 * Updates execute permission of key executable files.
	 * 
	 * @param problogDirectory
	 *            directory where ProbLog is being installed
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	void updatePermissions(String problogDirectory) throws IOException {
		Objects.requireNonNull(problogDirectory);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_WINDOWS)).setExecutable(true);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_DARWIN)).setExecutable(true);
		(new File(problogDirectory + FILE_SEPARATOR + PROBLOG_EXEC_LINUX)).setExecutable(true);
	}

	/**
	 * Installs ProbLog. This is necessary because just decompressing the ZIP
	 * file is not enough.
	 * 
	 * @param problogDirectory
	 *            directory where ProbLog has been installed
	 * @return the exit value given by the operating system
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 */
	int installProblog(String problogDirectory) throws IOException, InterruptedException {
		String[] args = new String[2];
		args[0] = problogDirectory + FILE_SEPARATOR + PROBLOG_CLI;
		args[1] = PROBLOG_INSTALL_COMMAND;
		return run(args);
	}

	public String getProblogDirectory() {
		return this.problogDirectory;
	}

	/**
	 * Downloads and installs ProbLog.
	 * 
	 * @throws IOException
	 *             if something goes wrong with I/O
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 */
	public void install() throws IOException, InterruptedException {
		if (!this.pythonMode) {
			DownloadManager jythonDownloadManager = new DownloadManager(DEFAULT_JYTHON_DOWNLOAD_URI,
					DEFAULT_JYTHON_FILE);
			jythonDownloadManager.downloadIfNecessary();
		}

		DownloadManager problogDownloadManager = new DownloadManager(DEFAULT_PROBLOG_DOWNLOAD_URI,
				DEFAULT_PROBLOG_ZIP_FILE);
		problogDownloadManager.downloadIfNecessary();

		String directory = decompressProblog(problogDownloadManager.getFileName(),
				DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
		this.problogDirectory = DEFAULT_PROBLOG_INSTALLATION_DIRECTORY + FILE_SEPARATOR + directory;
		updatePermissions(problogDirectory);
		installProblog(problogDirectory);
	}

	/**
	 * Starts the installation of ProbLog.
	 */
	public void startInstallation() {
		Thread thread = new Thread() {
			public void run() {
				synchronized (problogInstallationMonitor) {
					try {
						install();
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
	 * @param outputFileName
	 *            file name of output
	 * @return the exit value given by the operating system
	 */
	public int execute(String outputFileName) {
		Objects.requireNonNull(outputFileName);
		synchronized (this.problogInstallationMonitor) {
			String[] args = new String[4];
			args[0] = this.problogDirectory + FILE_SEPARATOR + PROBLOG_CLI;
			args[1] = (new File(DEFAULT_INPUT_FILE_FOR_PROBLOG)).getAbsolutePath();
			args[2] = PROBLOG_OUTPUT_OPTION;
			args[3] = (new File(outputFileName)).getAbsolutePath();
			return run(args);
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

	String tryExecution() throws IOException {
		File outputFile = new File(DEFAULT_OUTPUT_FILE_FROM_PROBLOG);
		outputFile.delete();
		execute(DEFAULT_OUTPUT_FILE_FROM_PROBLOG);
		if (outputFile.exists()) {
			return show(new InputStreamReader(new FileInputStream(outputFile)));
		} else {
			return NO_RESULTS;
		}
	}

	@Override
	public String apply(String input) {
		Objects.requireNonNull(input);
		try {
			createInputFileForProblog(input, DEFAULT_INPUT_FILE_FOR_PROBLOG);
			String ret = "";
			for (int iteration = 0; iteration < KNOWN_ISSUE_MAX_RETRIES
					&& (ret.isEmpty() || ret.trim().equals(KNOWN_ISSUE_MESSAGE)); iteration++) {
				ret = tryExecution();
			}
			return ret;
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
