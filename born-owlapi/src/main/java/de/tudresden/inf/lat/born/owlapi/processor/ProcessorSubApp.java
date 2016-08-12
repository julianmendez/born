package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.core.term.SubApp;
import de.tudresden.inf.lat.jproblog.JProblog;

/**
 * An object of this class processes an OWL ontology, produces a ProbLog file,
 * and executes ProbLog to obtain the result.
 * 
 * @see ProcessorCore
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorSubApp implements SubApp {

	static final String LOGGING_OPTION = "--log";
	public static final String COMMAND = "get";
	static final String HELP = ""
			+ "\nParameters: [--log] <ontology file> <Bayesian network file> <query file> <output file> [<ProbLog directory>]"
			+ "\n"
			+ "\n  <ontology file>          : file name of the probabilistic ontology, i.e. the OWL file with annotations"
			+ "\n  <Bayesian network file>  : file name of the Bayesian network"
			+ "\n  <query file>             : file name of the query"
			+ "\n  <output file>            : file name of the output"
			+ "\n  <ProbLog directory>      : (optional) directory where ProbLog is installed" + "\n" + "\n Option:"
			+ "\n   --log                   : shows log" + "\n" + "\nExamples:" + "\n" + "\n Execution:"
			+ "\n  java -jar born.jar " + COMMAND + " ontology.owl network.pl query.pl output.pl" + "\n"
			+ "\n Execution not showing log:" + "\n  java -jar born.jar " + COMMAND
			+ " ontology.owl network.pl query.pl output.pl" + "\n" + "\n Execution showing log:"
			+ "\n  java -jar born.jar " + COMMAND + " --log ontology.owl network.pl query.pl output.pl" + "\n"
			+ "\n Example of Bayesian network:" + "\n  0.58::x1." + "\n  0.35::x2." + "\n" + "\n Example of query:"
			+ "\n  query(sub('A', 'C'))." + "\n" + "\n" + "\n"
			+ "\nNote: this program requires an Internet connection to install ProbLog." + "\n" //
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
		Objects.requireNonNull(args);
		return (((args.length == 4) && !args[0].equals(LOGGING_OPTION))
				|| ((args.length == 5) && args[0].equals(LOGGING_OPTION)));
	}

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public String run(String[] args) {
		Objects.requireNonNull(args);
		long start = System.nanoTime();
		if (isValid(args)) {
			ProcessorConfiguration conf = new ProcessorConfigurationImpl();

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
				conf.setOntology(ProcessorConfigurationImpl.readOntology(new FileInputStream(newArgs[0])));
				conf.setBayesianNetwork(ProcessorConfigurationImpl.read(new FileReader(newArgs[1])));
				conf.setQuery(ProcessorConfigurationImpl.read(new FileReader(newArgs[2])));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} catch (OWLOntologyCreationException e) {
				throw new RuntimeException(e);
			}
			conf.setOutputFileName(newArgs[3]);

			JProblog queryProcessor = new JProblog();
			conf.setQueryProcessor(queryProcessor);

			ProcessorCore core = new ProcessorCore();
			ProcessorExecutionResult executionResult = new ProcessorExecutionResultImpl();
			core.run(conf, start, executionResult);
			sbuf.append(executionResult.getResult());

			return sbuf.toString();
		} else {
			return getHelp();
		}
	}

}
