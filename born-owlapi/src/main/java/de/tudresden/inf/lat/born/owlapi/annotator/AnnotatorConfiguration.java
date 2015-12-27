package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.InputStream;
import java.io.OutputStream;

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