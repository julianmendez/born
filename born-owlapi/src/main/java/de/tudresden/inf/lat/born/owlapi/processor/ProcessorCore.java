package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.core.term.Symbol;

/**
 * An object of this class processes an OWL ontology, produces a ProbLog file,
 * and executes ProbLog to obtain the result.
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorCore {

	static final String PROBLOG_CLI = "problog-cli.py";
	static final String PROBLOG_OUTPUT_FILE = Symbol.FILE_SEPARATOR + "tmp" + Symbol.FILE_SEPARATOR
			+ "~tmp-output.pl.tmp";
	static final String PROBLOG_OUTPUT_OPTION = "-o";
	static final String PYTHON = "python";
	static final String SPACE = " ";
	static final String LONG_TAB = "\t    : ";

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
		Objects.requireNonNull(sbuf);
		Objects.requireNonNull(input);
		BufferedReader reader = new BufferedReader(input);
		for (String line = reader.readLine(); !Objects.isNull(line); line = reader.readLine()) {
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
		Objects.requireNonNull(str);
		Objects.requireNonNull(start);
		long current = System.nanoTime();
		String info = "" + (current - start) + LONG_TAB + str;
		if (this.isShowingLog) {
			System.out.println(info);
		}
	}

	/**
	 * Creates the content of the ProbLog input file and returns this content as
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
	 * @return the content of the ProbLog input file
	 * @throws OWLOntologyCreationException
	 *             if the ontology was not created
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	String createProblogFile(long start, boolean useOfDefaultCompletionRules, String additionalCompletionRules,
			OWLOntology ontology, String bayesianNetwork, String query, ProcessorExecutionResult executionResult)
					throws OWLOntologyCreationException, IOException {
		Objects.requireNonNull(additionalCompletionRules);
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(bayesianNetwork);
		Objects.requireNonNull(query);
		log("Create ProbLog file.", start);
		ProblogInputCreator instance = new ProblogInputCreator();
		String ret = instance.createProblogFile(useOfDefaultCompletionRules, additionalCompletionRules, ontology,
				bayesianNetwork, query, new FileOutputStream(PROBLOG_OUTPUT_FILE), executionResult);
		return ret;

	}

	public void run(ProcessorConfiguration conf, long start, ProcessorExecutionResult executionResult) {
		long processorStart = System.nanoTime();
		Objects.requireNonNull(conf);
		Objects.requireNonNull(executionResult);
		StringBuffer sbuf = new StringBuffer();
		try {
			log("Start. Each row shows nanoseconds from start and task that is starting.", start);

			QueryProcessor queryProcessor = conf.getQueryProcessor();

			String info = createProblogFile(start, conf.hasDefaultCompletionRules(),
					conf.getAdditionalCompletionRules(), conf.getOntology(), conf.getBayesianNetwork(), conf.getQuery(),
					executionResult);
			log(info, start);

			long queryProcessingStart = System.nanoTime();
			int exitVal = queryProcessor.execute(start, conf.getOutputFileName());
			executionResult.setProblogReasoningTime(System.nanoTime() - queryProcessingStart);

			log("End and show results.", start);

			File outputFile = new File(conf.getOutputFileName());
			if (outputFile.exists()) {
				show(sbuf, new InputStreamReader(new FileInputStream(outputFile)));

			} else {
				sbuf.append("No results. Exit value: '" + exitVal + "'.");

			}
		} catch (IOException | OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}

		executionResult.setResult(sbuf.toString());
		executionResult.setTotalTime(System.nanoTime() - processorStart);
	}

}
