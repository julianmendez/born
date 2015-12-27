package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 
 * @author Julian Mendez
 *
 */
public class AnnotatorConfigurationImpl implements AnnotatorConfiguration {

	private InputStream inputOntology;
	private OutputStream outputOntology;
	private double threshold;
	private int maxNumberOfVars;

	@Override
	public InputStream getInputOntology() {
		return inputOntology;
	}

	@Override
	public void setInputOntology(InputStream inputOntology) {
		Objects.requireNonNull(inputOntology);
		this.inputOntology = inputOntology;
	}

	@Override
	public OutputStream getOutputOntology() {
		return outputOntology;
	}

	@Override
	public void setOutputOntology(OutputStream outputOntology) {
		Objects.requireNonNull(outputOntology);
		this.outputOntology = outputOntology;
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
	public int getMaxNumberOfVars() {
		return maxNumberOfVars;
	}

	@Override
	public void setMaxNumberOfVars(int maxNumberOfVars) {
		this.maxNumberOfVars = maxNumberOfVars;
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
					&& getMaxNumberOfVars() == other.getMaxNumberOfVars();
		}
	}

	@Override
	public int hashCode() {
		return this.inputOntology.hashCode() + 0x1F * (this.outputOntology.hashCode()
				+ 0x1F * ((new Double(this.threshold)).hashCode() + 0x1F * this.maxNumberOfVars));
	}

	@Override
	public String toString() {
		return this.inputOntology.toString() + " " + this.outputOntology.toString() + " " + this.threshold + " "
				+ this.maxNumberOfVars;
	}

}
