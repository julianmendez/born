package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.main.Decompressor;

/**
 * An object of this class processes an OWL ontology, produces a Problog file,
 * and executes Problog to obtain the result.
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorCore {

	static final String SLASH = "/";
	static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI
			.create("https://mips-build.cs.kuleuven.be/jenkins/job/problog2/lastSuccessfulBuild/artifact/problog.zip");
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

	/**
	 * Constructs a new processor.
	 */
	public ProcessorCore() {
	}

	/**
	 * Reads all the content provided by a reader and stores it in a string
	 * buffer.
	 * 
	 * @param sbuf
	 *            string buffer to store the content provided by the reader
	 * @param input
	 *            reader
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	void show(StringBuffer sbuf, Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			sbuf.append(line);
			sbuf.append(Symbol.NEW_LINE_CHAR);
		}
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
	 * Downloads Problog from the default download URI.
	 * 
	 * @param start
	 *            execution start
	 * @param problogZipFile
	 *            file name of the Problog ZIP file
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
	 * Decompresses the Problog ZIP file.
	 * 
	 * @param start
	 *            execution start
	 * @param problogZipFile
	 *            file name of Problog ZIP file
	 * @param problogDirectory
	 *            directory where Problog is being installed
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	void decompressProblog(long start, String problogZipFile, String problogDirectory) throws IOException {
		log("Decompress ProbLog.", start);
		Decompressor installer = new Decompressor();
		installer.decompress(new File(problogZipFile), new File(problogDirectory));
	}

	/**
	 * Updates execute permission of key executable files.
	 * 
	 * @param start
	 *            execution start
	 * @param problogDirectory
	 *            directory where Problog is being installed
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
	 * Installs Problog. This is necessary because just decompressing the ZIP
	 * file is not enough.
	 * 
	 * @param start
	 *            execution start
	 * @param problogDirectory
	 *            directory where Problog has been installed
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

	/**
	 * Creates the content of the Problog input file and returns this content as
	 * a string.
	 * 
	 * @param start
	 *            execution start
	 * @param ontology
	 *            OWL ontology
	 * @param bayesianNetwork
	 *            Bayesian network
	 * @param query
	 *            query
	 * @return the content of the Problog input file
	 * @throws OWLOntologyCreationException
	 *             if the ontology was not created
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	String createProblogFile(long start, OWLOntology ontology, String bayesianNetwork, String query)
			throws OWLOntologyCreationException, IOException {
		log("Create ProbLog file.", start);
		ProblogInputCreator instance = new ProblogInputCreator();
		String ret = instance.createProblogFile(ontology, bayesianNetwork, query,
				new FileOutputStream(PROBLOG_OUTPUT_FILE));
		return ret;

	}

	/**
	 * Executes Problog and returns the exit value given by the operating
	 * system.
	 * 
	 * @param start
	 *            execution start
	 * @param problogDirectory
	 *            directory where Problog is installed
	 * @param outputFileName
	 *            file name of output
	 * @return the exit value given by the operating system
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	int executeProblog(long start, String problogDirectory, String outputFileName)
			throws InterruptedException, IOException {
		log("Execute ProbLog.", start);
		Runtime runtime = Runtime.getRuntime();
		String commandLine = PYTHON + SPACE + problogDirectory + SLASH + PROBLOG_CLI + SPACE
				+ (new File(PROBLOG_OUTPUT_FILE)).getAbsolutePath() + SPACE + PROBLOG_OUTPUT_OPTION + SPACE
				+ (new File(outputFileName)).getAbsolutePath();
		log(commandLine, start);
		Process process = runtime.exec(commandLine);
		return process.waitFor();
	}

	public String run(ProcessorConfiguration conf, long start) {
		StringBuffer sbuf = new StringBuffer();
		try {
			log("Start. Each row shows nanoseconds from start and task that is starting.", start);

			if (conf.isProblogNeeded()) {
				downloadProblog(start, DEFAULT_PROBLOG_ZIP_FILE);
				decompressProblog(start, DEFAULT_PROBLOG_ZIP_FILE, DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
				updatePermissions(start, conf.getProblogDirectory());
				installProblog(start, conf.getProblogDirectory());
			}

			String info = createProblogFile(start, conf.getOntology(), conf.getBayesianNetwork(), conf.getQuery());
			log(info, start);

			int exitVal = executeProblog(start, conf.getProblogDirectory(), conf.getOutputFileName());

			log("End and show results.", start);

			File outputFile = new File(conf.getOutputFileName());
			if (outputFile.exists()) {
				show(sbuf, new InputStreamReader(new FileInputStream(outputFile)));

			} else {
				sbuf.append("No results. Exit value: '" + exitVal + "'.");

			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return sbuf.toString();
	}

}
