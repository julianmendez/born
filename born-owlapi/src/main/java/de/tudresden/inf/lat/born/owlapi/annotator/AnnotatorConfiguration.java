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

	InputStream getInputOntology();

	void setInputOntology(InputStream inputOntology);

	OutputStream getOutputOntology();

	void setOutputOntology(OutputStream outputOntology);

	double getThreshold();

	void setThreshold(double threshold);

	int getMaxNumberOfVars();

	void setMaxNumberOfVars(int maxNumberOfVars);

}
