package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.InputStream;
import java.io.OutputStream;

public interface SplitterConfiguration {

	InputStream getInputOntology();

	void setInputOntology(InputStream inputOntology);

	OutputStream getOutputOntology();

	void setOutputOntology(OutputStream outputOntology);

	OutputStream getBayesianNetwork();

	void setBayesianNetwork(OutputStream bayesianNetwork);

}
