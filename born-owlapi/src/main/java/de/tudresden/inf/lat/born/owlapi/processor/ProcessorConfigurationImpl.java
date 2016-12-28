package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;
import java.util.function.Function;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.term.Symbol;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorConfigurationImpl implements ProcessorConfiguration {

	private boolean hasDefaultCompletionRules = true;
	private String completionRules = "";
	private OWLOntology ontology;
	private String bayesianNetwork = "";
	private String query = "";
	private String outputFileName = "";
	private Function<String, String> queryProcessor = null;
	private boolean showingLog = true;

	/**
	 * Constructs a new processor configuration.
	 */
	public ProcessorConfigurationImpl() {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			this.ontology = manager.createOntology();
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasDefaultCompletionRules() {
		return this.hasDefaultCompletionRules;
	}

	@Override
	public void setUseOfDefaultCompletionRules(boolean useOfRules) {
		this.hasDefaultCompletionRules = useOfRules;
	}

	@Override
	public String getAdditionalCompletionRules() {
		return this.completionRules;
	}

	@Override
	public void setAdditionalCompletionRules(String completionRules) {
		this.completionRules = Objects.requireNonNull(completionRules);
	}

	@Override
	public OWLOntology getOntology() {
		return ontology;
	}

	@Override
	public void setOntology(OWLOntology ontologyInputStream) {
		this.ontology = Objects.requireNonNull(ontologyInputStream);
	}

	@Override
	public String getBayesianNetwork() {
		return bayesianNetwork;
	}

	@Override
	public void setBayesianNetwork(String bayesianNetwork) {
		this.bayesianNetwork = Objects.requireNonNull(bayesianNetwork);
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getOutputFileName() {
		return outputFileName;
	}

	@Override
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = Objects.requireNonNull(outputFileName);
	}

	@Override
	public Function<String, String> getQueryProcessor() {
		return queryProcessor;
	}

	@Override
	public void setQueryProcessor(Function<String, String> queryProcessor) {
		this.queryProcessor = Objects.requireNonNull(queryProcessor);
	}

	@Override
	public boolean isShowingLog() {
		return showingLog;
	}

	@Override
	public void setShowingLog(boolean showingLog) {
		this.showingLog = showingLog;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof ProcessorConfiguration)) {
			return false;
		} else {
			ProcessorConfiguration other = (ProcessorConfiguration) obj;
			return (hasDefaultCompletionRules() == other.hasDefaultCompletionRules())
					&& getAdditionalCompletionRules().equals(other.getAdditionalCompletionRules())
					&& getOntology().equals(other.getOntology())
					&& getBayesianNetwork().equals(other.getBayesianNetwork()) && getQuery().equals(other.getQuery())
					&& getOutputFileName().equals(other.getOutputFileName())
					&& getQueryProcessor().equals(other.getQueryProcessor())
					&& (isShowingLog() == other.isShowingLog());
		}
	}

	@Override
	public int hashCode() {
		return (this.hasDefaultCompletionRules ? 1 : 0) + //
				0x1F * (this.completionRules.hashCode() + //
						0x1F * (this.ontology.hashCode() + //
								0x1F * (this.bayesianNetwork.hashCode() + //
										0x1F * (this.query.hashCode() + //
												0x1F * (this.outputFileName.hashCode() + //
														0x1F * (this.queryProcessor.hashCode() + //
																0x1F * (this.showingLog ? 1 : 0)))))));
	}

	@Override
	public String toString() {
		return this.hasDefaultCompletionRules + "  " + this.completionRules + " " + this.ontology + " "
				+ this.bayesianNetwork + " " + this.query + " " + this.outputFileName + " " + this.queryProcessor + " "
				+ this.showingLog;
	}

	/**
	 * Returns a string after reading the reader.
	 * 
	 * @param reader
	 *            reader
	 * @return a string after reading the reader
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public static String read(Reader reader) throws IOException {
		Objects.requireNonNull(reader);
		StringBuffer sbuf = new StringBuffer();
		BufferedReader input = new BufferedReader(reader);
		for (String line = input.readLine(); Objects.nonNull(line); line = input.readLine()) {
			sbuf.append(line);
			sbuf.append(Symbol.NEW_LINE_CHAR);
		}
		return sbuf.toString();
	}

	/**
	 * Transfers the content read from a Reader to a Writer.
	 * 
	 * @param reader
	 *            reader
	 * @param writer
	 *            writer
	 * @throws IOException
	 *             if something goes wrong with I/O
	 */
	public static void write(Reader reader, Writer writer) throws IOException {
		Objects.requireNonNull(reader);
		Objects.requireNonNull(writer);
		BufferedWriter output = new BufferedWriter(writer);
		BufferedReader input = new BufferedReader(reader);
		for (String line = input.readLine(); Objects.nonNull(line); line = input.readLine()) {
			output.write(line);
			output.newLine();
		}
		output.flush();
	}

	/**
	 * Returns an OWL ontology after reading the input stream.
	 * 
	 * @param input
	 *            input stream
	 * @return an OWL ontology after reading the input stream
	 * @throws OWLOntologyCreationException
	 *             if something goes wrong with ontology creation
	 */
	public static OWLOntology readOntology(InputStream input) throws OWLOntologyCreationException {
		Objects.requireNonNull(input);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(input);
	}

	/**
	 * Returns a document format that contains the prefixes of the given
	 * ontology.
	 * 
	 * @param ontology
	 *            ontology
	 * @return a document format that contains the prefixes of the given
	 *         ontology
	 */
	public static PrefixDocumentFormat getPrefixes(OWLOntology ontology) {
		OWLOntologyManager manager = ontology.getOWLOntologyManager();
		return manager.getOntologyFormat(ontology).asPrefixOWLOntologyFormat();
	}

}
