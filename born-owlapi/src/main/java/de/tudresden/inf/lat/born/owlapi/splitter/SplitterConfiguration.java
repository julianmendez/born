package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface models the configuration of a splitter.
 * 
 * @author Julian Mendez
 *
 */
public interface SplitterConfiguration {

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
	 * Returns the Bayesian network.
	 * 
	 * @return the Bayesian network
	 */
	OutputStream getBayesianNetwork();

	/**
	 * Sets the Bayesian network.
	 * 
	 * @param bayesianNetwork
	 *            Bayesian network
	 */
	void setBayesianNetwork(OutputStream bayesianNetwork);

}
