package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface models the configuration of an annotator.
 * 
 * @author Julian Mendez
 *
 */
public interface AnnotatorConfiguration {

	/**
	 * Returns the input ontology.
	 * 
	 * @return the input ontology
	 */
	InputStream getInputOntology();

	/**
	 * Sets the input ontology.
	 * 
	 * @param inputOntology
	 *            input ontology
	 */
	void setInputOntology(InputStream inputOntology);

	/**
	 * Returns the output ontology.
	 * 
	 * @return the output ontology
	 */
	OutputStream getOutputOntology();

	/**
	 * Sets the output ontology.
	 * 
	 * @param outputOntology
	 *            output ontology
	 */
	void setOutputOntology(OutputStream outputOntology);

	/**
	 * Returns the threshold.
	 * 
	 * @return the threshold
	 */
	double getThreshold();

	/**
	 * Sets the threshold.
	 * 
	 * @param threshold
	 *            threshold
	 */
	void setThreshold(double threshold);

	/**
	 * Returns the maximum number of variables.
	 * 
	 * @return the maximum number of variables
	 */
	int getMaxNumberOfVars();

	/**
	 * Sets the maximum number of variables.
	 * 
	 * @param maxNumberOfVars
	 *            the maximum number of variables
	 */
	void setMaxNumberOfVars(int maxNumberOfVars);

}
