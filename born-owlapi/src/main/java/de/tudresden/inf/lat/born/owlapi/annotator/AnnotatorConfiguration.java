package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @author Julian Mendez
 *
 */
public class AnnotatorConfiguration {

	private InputStream inputOntology;
	private OutputStream outputOntology;
	private double threshold;
	private int maxNumberOfVars;

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

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getMaxNumberOfVars() {
		return maxNumberOfVars;
	}

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
					&& getOutputOntology().equals(other.getOutputOntology())
					&& getThreshold() == other.getThreshold()
					&& getMaxNumberOfVars() == other.getMaxNumberOfVars();
		}
	}

	@Override
	public int hashCode() {
		return this.inputOntology.hashCode()
				+ 0x1F
				* (this.outputOntology.hashCode() + 0x1F * (Double
						.hashCode(this.threshold) + 0x1F * this.maxNumberOfVars));
	}

	@Override
	public String toString() {
		return this.inputOntology.toString() + " "
				+ this.outputOntology.toString() + " " + this.threshold + " "
				+ this.maxNumberOfVars;
	}

}
