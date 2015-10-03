package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorConfiguration {

	private OWLOntology ontology;
	private String bayesianNetwork;
	private String query;
	private String outputFileName;
	private String problogDirectory;
	private boolean problogNeeded = true;
	private boolean showingLog = true;

	public OWLOntology getOntology() {
		return ontology;
	}

	public void setOntology(OWLOntology ontologyInputStream) {
		this.ontology = ontologyInputStream;
	}

	public String getBayesianNetwork() {
		return bayesianNetwork;
	}

	public void setBayesianNetwork(String bayesianNetwork) {
		this.bayesianNetwork = bayesianNetwork;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getProblogDirectory() {
		return problogDirectory;
	}

	public void setProblogDirectory(String problogDirectory) {
		this.problogDirectory = problogDirectory;
	}

	public boolean isShowingLog() {
		return showingLog;
	}

	public void setShowingLog(boolean showingLog) {
		this.showingLog = showingLog;
	}

	public boolean isProblogNeeded() {
		return problogNeeded;
	}

	public void setProblogNeeded(boolean problogNeeded) {
		this.problogNeeded = problogNeeded;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof AnnotatorConfiguration)) {
			return false;
		} else {
			ProcessorConfiguration other = (ProcessorConfiguration) obj;
			return getOntology().equals(other.getOntology()) && getBayesianNetwork().equals(other.getBayesianNetwork())
					&& getQuery().equals(other.getQuery()) && getOutputFileName().equals(other.getOutputFileName())
					&& getProblogDirectory().equals(other.getProblogDirectory())
					&& (isShowingLog() == other.isShowingLog()) && (isProblogNeeded() == other.isProblogNeeded());
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.ontology + " " + this.bayesianNetwork + " " + this.query + " " + this.outputFileName + " "
				+ this.problogDirectory + " " + this.showingLog + " " + this.problogNeeded;
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
		StringBuffer sbuf = new StringBuffer();
		BufferedReader input = new BufferedReader(reader);
		for (String line = input.readLine(); line != null; line = input.readLine()) {
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
		BufferedWriter output = new BufferedWriter(writer);
		BufferedReader input = new BufferedReader(reader);
		for (String line = input.readLine(); line != null; line = input.readLine()) {
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
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		return manager.loadOntologyFromOntologyDocument(input);
	}

}
