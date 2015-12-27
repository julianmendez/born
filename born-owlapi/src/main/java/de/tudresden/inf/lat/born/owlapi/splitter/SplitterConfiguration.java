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

	InputStream getInputOntology();

	void setInputOntology(InputStream inputOntology);

	OutputStream getOutputOntology();

	void setOutputOntology(OutputStream outputOntology);

	OutputStream getBayesianNetwork();

	void setBayesianNetwork(OutputStream bayesianNetwork);

}
