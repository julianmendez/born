package de.tudresden.inf.lat.born.owlapi.processor;

import org.semanticweb.owlapi.model.OWLOntology;

public interface ProcessorConfiguration {

	OWLOntology getOntology();

	void setOntology(OWLOntology ontologyInputStream);

	String getBayesianNetwork();

	void setBayesianNetwork(String bayesianNetwork);

	String getQuery();

	void setQuery(String query);

	String getOutputFileName();

	void setOutputFileName(String outputFileName);

	QueryProcessor getQueryProcessor();

	void setQueryProcessor(QueryProcessor queryProcessor);

	boolean isShowingLog();

	void setShowingLog(boolean showingLog);

}