package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;

/**
 * 
 * @author Julian Mendez
 *
 */
public class AnnotatorConfigurationImpl implements AnnotatorConfiguration {

	private InputStream inputOntology;
	private Set<String> inputBayesianNetworkVariables;
	private OutputStream outputOntology;
	private double threshold;

	@Override
	public InputStream getInputOntology() {
		return inputOntology;
	}

	@Override
	public void setInputOntology(InputStream inputOntology) {
		this.inputOntology = Objects.requireNonNull(inputOntology);
	}

	@Override
	public OutputStream getOutputOntology() {
		return outputOntology;
	}

	@Override
	public void setOutputOntology(OutputStream outputOntology) {
		this.outputOntology = Objects.requireNonNull(outputOntology);
	}

	@Override
	public double getThreshold() {
		return threshold;
	}

	@Override
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public Set<String> getInputBayesianNetworkVariables() {
		return inputBayesianNetworkVariables;
	}

	@Override
	public void setInputBayesianNetworkVariables(Set<String> inputBayesianNetworkVariables) {
		this.inputBayesianNetworkVariables = Objects.requireNonNull(inputBayesianNetworkVariables);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof AnnotatorConfiguration)) {
			return false;
		} else {
			AnnotatorConfiguration other = (AnnotatorConfiguration) obj;
			return getInputOntology().equals(other.getInputOntology())
					&& getOutputOntology().equals(other.getOutputOntology()) && getThreshold() == other.getThreshold()
					&& getInputBayesianNetworkVariables().equals(other.getInputBayesianNetworkVariables());
		}
	}

	@Override
	public int hashCode() {
		return this.inputOntology.hashCode() + 0x1F * (this.outputOntology.hashCode() + 0x1F
				* ((new Double(this.threshold)).hashCode() + 0x1F * this.inputBayesianNetworkVariables.hashCode()));
	}

	@Override
	public String toString() {
		return this.inputOntology.toString() + " " + this.outputOntology.toString() + " " + this.threshold + " "
				+ this.inputBayesianNetworkVariables.toString();
	}

}
