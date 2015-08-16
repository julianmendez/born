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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof SplitterConfiguration)) {
			return false;
		} else {
			SplitterConfiguration other = (SplitterConfiguration) obj;
			return getInputOntology().equals(other.getInputOntology())
					&& getOutputOntology().equals(other.getOutputOntology())
					&& getBayesianNetwork().equals(other.getBayesianNetwork());
		}
	}

	@Override
	public int hashCode() {
		return this.inputOntology.hashCode()
				+ 0x1F
				* (this.outputOntology.hashCode() + 0x1F * this.bayesianNetwork
						.hashCode());
	}

	@Override
	public String toString() {
		return this.inputOntology.toString() + " "
				+ this.outputOntology.toString() + " "
				+ this.bayesianNetwork.toString();
	}

}
