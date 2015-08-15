package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author Julian Mendez
 *
 */
public class SplitterConfiguration {

	private InputStream inputOntology;
	private OutputStream outputOntology;
	private OutputStream bayesianNetwork;

	public InputStream getInputOntology() {
		return inputOntology;
	}

	public void setInputOntology(InputStream inputOntology) {
		this.inputOntology = inputOntology;
	}

	public OutputStream getOutputOntology() {
		return outputOntology;
	}

	public void setOutputOntology(OutputStream outputOntology) {
		this.outputOntology = outputOntology;
	}

	public OutputStream getBayesianNetwork() {
		return bayesianNetwork;
	}

	public void setBayesianNetwork(OutputStream bayesianNetwork) {
		this.bayesianNetwork = bayesianNetwork;
	}

}
