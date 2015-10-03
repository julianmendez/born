package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import de.tudresden.inf.lat.born.core.term.SubApp;

/**
 * An object of this class processes an OWL ontology, produces a Problog file,
 * and executes Problog to obtain the result.
 * 
 * @see ProcessorCore
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorSubApp implements SubApp {

	static final String LOGGING_OPTION = "--log";
	static final String SLASH = ProcessorCore.SLASH;
	static final URI DEFAULT_PROBLOG_DOWNLOAD_URI = ProcessorCore.DEFAULT_PROBLOG_DOWNLOAD_URI;
	static final String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = ProcessorCore.DEFAULT_PROBLOG_INSTALLATION_DIRECTORY;
	public static final String DEFAULT_PROBLOG_DIRECTORY = ProcessorCore.DEFAULT_PROBLOG_INSTALLATION_DIRECTORY
			+ SLASH + "problog2.1";
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
			+ "\n  java -jar born.jar get ontology.owl network.pl query.pl output.pl"
			+ "\n"
			+ "\n Execution with ProbLog installed:"
			+ "\n  java -jar born.jar get ontology.owl network.pl query.pl output.pl /opt/problog2.1"
			+ "\n"
			+ "\n Execution with ProbLog installed showing log:"
			+ "\n  java -jar born.jar get --log ontology.owl network.pl query.pl output.pl /opt/problog2.1"
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
			+ "\nPlease note that this option requires an Internet connection and the execution time can be longer."
			+ "\n" //
			+ "\n" //
			+ "\n";

	/**
	 * Constructs a new processor.
	 */
	public ProcessorSubApp() {
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

	String readFile(String fileName) throws IOException {
		StringBuffer ret = new StringBuffer();
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		for (String line = input.readLine(); line != null; line = input.readLine()) {
			ret.append(line);
			ret.append("\n");
		}
		input.close();
		return ret.toString();
	}

	@Override
	public String run(String[] args) {
		long start = System.nanoTime();
		if (isValid(args)) {
			ProcessorConfiguration conf = new ProcessorConfiguration();

			StringBuffer sbuf = new StringBuffer();
			String[] newArgs = null;
			if (args[0].equals(LOGGING_OPTION)) {
				newArgs = new String[args.length - 1];
				System.arraycopy(args, 1, newArgs, 0, args.length - 1);
				conf.setShowingLog(true);
			} else {
				newArgs = new String[args.length];
				System.arraycopy(args, 0, newArgs, 0, args.length);
				conf.setShowingLog(false);
			}

			try {
				conf.setOntologyInputStream(new FileInputStream(newArgs[0]));
				conf.setBayesianNetworkInputStream(new FileInputStream(
						newArgs[1]));
				conf.setQuery(readFile(newArgs[2]));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			conf.setOutputFileName(newArgs[3]);
			conf.setProblogDirectory(null);

			if (newArgs.length == 5) {
				conf.setProblogDirectory(newArgs[4]);
				conf.setProblogNeeded(false);
			} else {
				conf.setProblogDirectory(DEFAULT_PROBLOG_DIRECTORY);
				conf.setProblogNeeded(true);
			}

			ProcessorCore core = new ProcessorCore();
			String result = core.run(conf, start);
			sbuf.append(result);

			return sbuf.toString();
		} else {
			return getHelp();
		}
	}

}
