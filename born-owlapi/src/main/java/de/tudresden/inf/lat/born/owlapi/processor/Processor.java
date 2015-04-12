package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.core.term.SubApp;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.main.Decompressor;

/**
 * An object of this class processes an OWL ontology, produces a Problog file,
 * and executes Problog to obtain the result.
 * 
 * @author Julian Mendez
 *
 */
public class Processor implements SubApp {

	static final String LOGGING_OPTION = "--log";
	static final String SLASH = "/";
	static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI
			.create("https://mips-build.cs.kuleuven.be/jenkins/job/problog2/lastSuccessfulBuild/artifact/problog.zip");
	static final String DEFAULT_PROBLOG_ZIP_FILE = "problog-2.1-SNAPSHOT.zip";
	static final String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = "/tmp";
	static final String DEFAULT_PROBLOG_DIRECTORY = DEFAULT_PROBLOG_INSTALLATION_DIRECTORY
			+ SLASH + "problog2.1";
	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_INSTALL_COMMAND = "install";
	static final String PROBLOG_OUTPUT_FILE = "/tmp/~tmp-output.pl.tmp";
	static final String PROBLOG_OUTPUT_OPTION = "-o";
	static final String PYTHON = "python";
	static final String SPACE = " ";
	static final String LONG_TAB = "\t    : ";
	static final String TITLE = "Time from start (ns)" + LONG_TAB + "Task";
	static final String HELP = ""
			+ "\nParameters: [--log] <ontology file> <Bayesian network file> <query file> <output file> [<ProbLog directory>]"
			+ "\n"
			+ "\n  <ontology file>          : file name of the probabilistic ontology, i.e. the OWL file with annotations"
			+ "\n  <Bayesian network file>  : file name of the Bayesian network"
			+ "\n  <query file>             : file name of the query"
			+ "\n  <output file>            : file name of the output"
			+ "\n  <ProbLog directory>      : (optional) directory where ProbLog is installed"
			+ "\n"
			+ "\n Option:"
			+ "\n   --log                   : shows log"
			+ "\n"
			+ "\nExamples:"
			+ "\n"
			+ "\n Execution without ProbLog installed:"
			+ "\n  java -jar born.jar ontology.owl network.pl query.pl output.pl"
			+ "\n"
			+ "\n Execution with ProbLog installed:"
			+ "\n  java -jar born.jar ontology.owl network.pl query.pl output.pl /opt/problog2.1"
			+ "\n"
			+ "\n Execution with ProbLog installed showing log:"
			+ "\n  java -jar born.jar --log ontology.owl network.pl query.pl output.pl /opt/problog2.1"
			+ "\n"
			+ "\n Bayesian network:"
			+ "\n  0.58::x1."
			+ "\n  0.35::x2."
			+ "\n"
			+ "\n Query:"
			+ "\n  query(sub('A', 'C'))."
			+ "\n"
			+ "\n"
			+ "\n"
			+ "\nNote: this program requires the following installed:"
			+ "\n - Java 8"
			+ "\n - ProbLog 2.1"
			+ "\n - Python 2.7+ or 3.2+"
			+ "\n"
			+ "\nIf ProbLog is not installed, this program downloads ProbLog from:"
			+ "\n   "
			+ DEFAULT_PROBLOG_DOWNLOAD_URI
			+ "\nPlease, note that this option requires an Internet connection and the execution time can take longer."
			+ "\n" //
			+ "\n" //
			+ "\n";

	private boolean isShowingLog = false;

	/**
	 * Constructs a new processor.
	 */
	public Processor() {
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
	 *             if something went wrong with the I/O
	 */
	void show(StringBuffer sbuf, Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
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
	 *             if something went wrong with the I/O
	 * @throws URISyntaxException
	 *             if the default URI is not valid
	 */
	void downloadProblog(long start, String problogZipFile) throws IOException,
			URISyntaxException {
		log("Download ProbLog.", start);
		ReadableByteChannel channel = Channels
				.newChannel(DEFAULT_PROBLOG_DOWNLOAD_URI.toURL().openStream());
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
	 *             if something went wrong with the I/O
	 */
	void decompressProblog(long start, String problogZipFile,
			String problogDirectory) throws IOException {
		log("Decompress ProbLog.", start);
		Decompressor installer = new Decompressor();
		installer.decompress(new File(problogZipFile), new File(
				problogDirectory));
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
	 *             if something went wrong with the I/O
	 * @throws InterruptedException
	 *             if the execution was interrupted
	 */
	int installProblog(long start, String problogDirectory) throws IOException,
			InterruptedException {
		log("Install ProbLog.", start);
		String commandLine = PYTHON + SPACE + problogDirectory + SLASH
				+ PROBLOG_CLI + SPACE + PROBLOG_INSTALL_COMMAND;
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
	 * @param ontologyFileName
	 *            file name of the OWL ontology
	 * @param bayesianNetworkFileName
	 *            file name of the Bayesian network
	 * @param queryFileName
	 *            file name of the query
	 * @return the content of the Problog input file
	 * @throws OWLOntologyCreationException
	 *             if the ontology was not created
	 * @throws FileNotFoundException
	 *             if the file was not found
	 * @throws IOException
	 *             if something went wrong with the I/O
	 */
	String createProblogFile(long start, String ontologyFileName,
			String bayesianNetworkFileName, String queryFileName)
			throws OWLOntologyCreationException, FileNotFoundException,
			IOException {
		log("Create ProbLog file.", start);
		ProblogInputCreator instance = new ProblogInputCreator();
		String ret = instance.createProblogFile(new FileInputStream(
				ontologyFileName),
				new FileInputStream(bayesianNetworkFileName),
				new FileInputStream(queryFileName), new FileOutputStream(
						PROBLOG_OUTPUT_FILE));
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
	 *             if something went wrong with the I/O
	 */
	int executeProblog(long start, String problogDirectory,
			String outputFileName) throws InterruptedException, IOException {
		log("Execute ProbLog.", start);
		Runtime runtime = Runtime.getRuntime();
		String commandLine = PYTHON + SPACE + problogDirectory + SLASH
				+ PROBLOG_CLI + SPACE
				+ (new File(PROBLOG_OUTPUT_FILE)).getAbsolutePath() + SPACE
				+ PROBLOG_OUTPUT_OPTION + SPACE
				+ (new File(outputFileName)).getAbsolutePath();
		log(commandLine, start);
		Process process = runtime.exec(commandLine);
		return process.waitFor();
	}

	@Override
	public boolean isValid(String[] args) {
		return (((args.length == 4) && !args[0].equals(LOGGING_OPTION))
				|| (args.length == 5) || ((args.length == 6) && args[0]
				.equals(LOGGING_OPTION)));
	}

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public String run(String[] args) {
		long start = System.nanoTime();
		if (isValid(args)) {
			StringBuffer sbuf = new StringBuffer();
			try {
				String[] newArgs = null;
				if (args[0].equals(LOGGING_OPTION)) {
					newArgs = new String[args.length - 1];
					System.arraycopy(args, 1, newArgs, 0, args.length - 1);
					this.isShowingLog = true;
				} else {
					newArgs = new String[args.length];
					System.arraycopy(args, 0, newArgs, 0, args.length);
					this.isShowingLog = false;
				}

				log("Start. Each row shows nanoseconds from start and task that is starting.",
						start);

				String ontologyFileName = newArgs[0];
				String bayesianNetworkFileName = newArgs[1];
				String queryFileName = newArgs[2];
				String outputFileName = newArgs[3];
				String problogDirectory = null;

				if (newArgs.length == 5) {
					problogDirectory = newArgs[4];
				} else {
					problogDirectory = DEFAULT_PROBLOG_DIRECTORY;
					downloadProblog(start, DEFAULT_PROBLOG_ZIP_FILE);
					decompressProblog(start, DEFAULT_PROBLOG_ZIP_FILE,
							DEFAULT_PROBLOG_INSTALLATION_DIRECTORY);
					installProblog(start, problogDirectory);
				}

				String info = createProblogFile(start, ontologyFileName,
						bayesianNetworkFileName, queryFileName);
				log(info, start);

				int exitVal = executeProblog(start, problogDirectory,
						outputFileName);

				log("End and show results.", start);

				File outputFile = new File(outputFileName);
				if (outputFile.exists()) {
					show(sbuf, new InputStreamReader(new FileInputStream(
							outputFile)));

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
		} else {
			return getHelp();
		}
	}

}
